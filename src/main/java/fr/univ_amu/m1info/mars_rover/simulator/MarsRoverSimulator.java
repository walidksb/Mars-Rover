package fr.univ_amu.m1info.mars_rover.simulator;

import fr.univ_amu.m1info.mars_rover.input.*;
import fr.univ_amu.m1info.mars_rover.output.MarsRoverOutput;
import fr.univ_amu.m1info.mars_rover.output.MarsRoverState;

import java.util.*;

public class MarsRoverSimulator {

    // Interface fonctionnelle pour observer la simulation à chaque étape.
    @FunctionalInterface
    public interface SimulationObserver {
        void onStep(List<Position> currentPositions, Set<Coordinates> explored);
    }

    // An observer that does nothing, to avoid null checks
    private static final SimulationObserver NO_OP_OBSERVER = (positions, explored) -> {};

    public MarsRoverOutput simulate(MarsRoverInput input) {
        return simulate(input, NO_OP_OBSERVER);
    }

    // la fonction simulate qui va simuler les movements du Rover
    public MarsRoverOutput simulate(MarsRoverInput input, SimulationObserver observer) {
        List<MarsRoverState> finalStates = new ArrayList<>();
        Set<Coordinates> explored = new HashSet<>();
        List<Position> initialPositions = new ArrayList<>();
        for (RoverConfiguration rover : input.rovers()) {
            initialPositions.add(rover.position());
        }

        for (Position p : initialPositions) {
            explored.add(p.coordinates());
        }
        observer.onStep(initialPositions, explored);

        for (RoverConfiguration rover : input.rovers()) {
            Position current = rover.position();
            boolean destroyed = false;

            explored.add(current.coordinates());
            GridConfiguration gridConf = input.grid();

            for (Command cmd : rover.commands()) {
                if (gridConf.kind() == GridKind.RECTANGULAR) {
                    current = executeRectangularMove(cmd, current, gridConf);
                    if (current == null) { // null means destroyed
                        destroyed = true;
                        break;
                    }
                } else if (gridConf.kind() == GridKind.TOROIDAL) {
                    current = executeToroidalMove(cmd, current, gridConf);
                }

                explored.add(current.coordinates());

                // Notifie l'observateur après chaque mouvement
                // On met à jour la position du rover actuel dans la liste des positions
                List<Position> currentPositions = new ArrayList<>(initialPositions);
                int roverIndex = input.rovers().indexOf(rover);
                if(roverIndex != -1) {
                    currentPositions.set(roverIndex, current);
                    observer.onStep(currentPositions, new HashSet<>(explored));
                }
            }
            finalStates.add(new MarsRoverState(destroyed, current));

            // Mise à jour de la position initiale pour le prochain rover
            int roverIndex = input.rovers().indexOf(rover);
            if(roverIndex != -1) {
                initialPositions.set(roverIndex, current);
            }
        }

        int totalCells = input.grid().width() * input.grid().height();
        double percentageExplored = (explored.size() * 100.0) / totalCells;

        // Final notification to show final state
        observer.onStep(initialPositions, explored);

        return new MarsRoverOutput(percentageExplored, finalStates);
    }

    // la methode pour executer le Grid Rectangular avec la commande Move
    private Position executeRectangularMove(Command cmd, Position current, GridConfiguration gridConf) {
        int maxX = gridConf.width() - 1;
        int maxY = gridConf.height() - 1;

        return switch (cmd) {
            case MOVE -> {
                int x = current.coordinates().x();
                int y = current.coordinates().y();
                switch (current.orientation()) {
                    case NORTH -> y++;
                    case SOUTH -> y--;
                    case EAST -> x++;
                    case WEST -> x--;
                }
                if (x < 0 || x > maxX || y < 0 || y > maxY) {
                    yield null; // le met comme destroyed
                }
                yield new Position(new Coordinates(x, y), current.orientation());
            }
            case LEFT -> new Position(current.coordinates(), turnLeft(current.orientation()));
            case RIGHT -> new Position(current.coordinates(), turnRight(current.orientation()));
        };
    }

    // la methode pour executer le Grid Toroidal avec la commande Move
    private Position executeToroidalMove(Command cmd, Position current, GridConfiguration gridConf) {
        return switch (cmd) {
            case MOVE -> {
                int x = current.coordinates().x();
                int y = current.coordinates().y();
                switch (current.orientation()) {
                    case NORTH -> y = (y + 1 + gridConf.height()) % gridConf.height();
                    case SOUTH -> y = (y - 1 + gridConf.height()) % gridConf.height();
                    case EAST -> x = (x + 1 + gridConf.width()) % gridConf.width();
                    case WEST -> x = (x - 1 + gridConf.width()) % gridConf.width();
                }
                yield new Position(new Coordinates(x, y), current.orientation());
            }
            case LEFT -> new Position(current.coordinates(), turnLeft(current.orientation()));
            case RIGHT -> new Position(current.coordinates(), turnRight(current.orientation()));
        };
    }

    private Direction turnLeft(Direction dir) {
        return switch (dir) {
            case NORTH -> Direction.WEST;
            case SOUTH -> Direction.EAST;
            case EAST -> Direction.NORTH;
            case WEST -> Direction.SOUTH;
        };
    }

    private Direction turnRight(Direction dir) {
        return switch (dir) {
            case NORTH -> Direction.EAST;
            case SOUTH -> Direction.WEST;
            case EAST -> Direction.SOUTH;
            case WEST -> Direction.NORTH;
        };
    }
}
