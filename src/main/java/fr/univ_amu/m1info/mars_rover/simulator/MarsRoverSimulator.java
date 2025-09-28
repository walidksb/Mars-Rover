package fr.univ_amu.m1info.mars_rover.simulator;

import fr.univ_amu.m1info.mars_rover.input.*;
import fr.univ_amu.m1info.mars_rover.input.Coordinates;
import fr.univ_amu.m1info.mars_rover.input.Position;
import fr.univ_amu.m1info.mars_rover.output.*;
import java.util.*;

public class MarsRoverSimulator {

    public MarsRoverOutput simulate(MarsRoverInput input) {
        List<MarsRoverState> finalStates = new ArrayList<>();
        Set<Coordinates> explored = new HashSet<>();

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
            }

            finalStates.add(new MarsRoverState(destroyed, current));
        }

        int totalCells = input.grid().width() * input.grid().height();
        double percentageExplored = (explored.size() * 100.0) / totalCells;

        return new MarsRoverOutput(percentageExplored, finalStates);
    }

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
                    case EAST  -> x++;
                    case WEST  -> x--;
                }
                if (x < 0 || x > maxX || y < 0 || y > maxY) {
                    yield null; // mark as destroyed
                }
                yield new Position(new Coordinates(x, y), current.orientation());
            }
            case LEFT -> new Position(current.coordinates(), turnLeft(current.orientation()));
            case RIGHT -> new Position(current.coordinates(), turnRight(current.orientation()));
        };
    }

    private Position executeToroidalMove(Command cmd, Position current, GridConfiguration gridConf) {
        return switch (cmd) {
            case MOVE -> {
                int x = current.coordinates().x();
                int y = current.coordinates().y();
                switch (current.orientation()) {
                    case NORTH -> y = (y + 1 + gridConf.height()) % gridConf.height();
                    case SOUTH -> y = (y - 1 + gridConf.height()) % gridConf.height();
                    case EAST  -> x = (x + 1 + gridConf.width()) % gridConf.width();
                    case WEST  -> x = (x - 1 + gridConf.width()) % gridConf.width();
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
            case EAST  -> Direction.NORTH;
            case WEST  -> Direction.SOUTH;
        };
    }

    private Direction turnRight(Direction dir) {
        return switch (dir) {
            case NORTH -> Direction.EAST;
            case SOUTH -> Direction.WEST;
            case EAST  -> Direction.SOUTH;
            case WEST  -> Direction.NORTH;
        };
    }
}

