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

    private Set<Coordinates> getExploredCells(Coordinates current, int radius, GridConfiguration gridConf) {
        Set<Coordinates> explored = new HashSet<>();
        int width = gridConf.width();
        int height = gridConf.height();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                // ici distance Manhattan (cases dans un losange)
                if (Math.abs(dx) + Math.abs(dy) <= radius) {
                    int newX = current.x() + dx;
                    int newY = current.y() + dy;

                    if (gridConf.kind() == GridKind.TOROIDAL) {
                        newX = (newX + width) % width;
                        newY = (newY + height) % height;
                    }

                    if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                        explored.add(new Coordinates(newX, newY));
                    }
                }
            }
        }
        return explored;
    }

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
            int radius = rover.explorationRadius(); // <--- rayon d’exploration du rover

            // Vérifie si la position initiale est un obstacle
            if (input.obstacles() != null && input.obstacles().contains(current.coordinates())) {
                destroyed = true;
                finalStates.add(new MarsRoverState(true, current));
                continue;
            }

            // Ajoute la cellule actuelle + rayon d’exploration
            GridConfiguration gridConf = input.grid();
            explored.addAll(getExploredCells(current.coordinates(), radius, gridConf));

            for (Command cmd : rover.commands()) {
                if (gridConf.kind() == GridKind.RECTANGULAR) {
                    current = executeRectangularMove(cmd, current, gridConf);
                    if (current == null) { // null = destroyed
                        destroyed = true;
                        break;
                    }
                } else if (gridConf.kind() == GridKind.TOROIDAL) {
                    current = executeToroidalMove(cmd, current, gridConf);
                }

                // Vérifie si la nouvelle position est un obstacle
                if (input.obstacles() != null && input.obstacles().contains(current.coordinates())) {
                    destroyed = true;
                    break;
                }

                // Ajoute la nouvelle position + rayon d’exploration
                explored.addAll(getExploredCells(current.coordinates(), radius, gridConf));

                // Notifie l’observateur
                List<Position> currentPositions = new ArrayList<>(initialPositions);
                int roverIndex = input.rovers().indexOf(rover);
                if (roverIndex != -1) {
                    currentPositions.set(roverIndex, current);
                    observer.onStep(currentPositions, new HashSet<>(explored));
                }
            }
            finalStates.add(new MarsRoverState(destroyed, current));

            int roverIndex = input.rovers().indexOf(rover);
            if (roverIndex != -1) {
                initialPositions.set(roverIndex, current);
            }
        }

        int totalCells = input.grid().width() * input.grid().height();
        double percentageExplored = (explored.size() * 100.0) / totalCells;

        // Final notification to show final state
        observer.onStep(initialPositions, explored);

        return new MarsRoverOutput(percentageExplored, finalStates, explored);
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
