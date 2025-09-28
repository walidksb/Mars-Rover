package fr.univ_amu.m1info.mars_rover.simulator;

import fr.univ_amu.m1info.mars_rover.input.*;
import fr.univ_amu.m1info.mars_rover.input.Coordinates;
import fr.univ_amu.m1info.mars_rover.input.Position;
import fr.univ_amu.m1info.mars_rover.output.*;
import java.util.*;

public class MarsRoverSimulator {

    public MarsRoverOutput simulate(MarsRoverInput input) {
        // Exemple simulation simplifiée
        List<MarsRoverState> finalStates = new ArrayList<>();
        // Set to keep all explored positions without repeating because its a set
        Set<Coordinates> explored = new HashSet<>();

        for (RoverConfiguration rover : input.rovers()) {
            Position current = rover.position();
            boolean destroyed = false;

            explored.add(current.coordinates());

            // Exécution fictive des commandes
            // we check if the grid kind is rectangular or tornoidal
            GridConfiguration gridConf = input.grid();

            // ////////////////////// RECTANGULAR /////////////////////////
            if (gridConf.kind() == GridKind.RECTANGULAR) {
                int i =0;
                int maxX = gridConf.width() - 1;
                int maxY = gridConf.height() - 1;
                while (i < rover.commands().size() && !destroyed) {
                    Command cmd = rover.commands().get(i);
                    switch (cmd) {
                        case MOVE -> {
                            // exemple : avancer de 1 vers la direction actuelle
                            Coordinates c = current.coordinates();
                            int x = c.x();
                            int y = c.y();
                            switch (current.orientation()) {
                                case NORTH -> y++;
                                case SOUTH -> y--;
                                case EAST -> x++;
                                case WEST -> x--;
                            }
                            current = new Position(new Coordinates(x, y), current.orientation());
                            // we check if the rover gets out of the grid so it will be marked as destroyed
                            // and we stop the command
                            if (x < 0 || x > maxX || y < 0 || y > maxY) {
                                destroyed = true;
                            } else {
                                current = new Position(new Coordinates(x, y), current.orientation());
                                explored.add(current.coordinates());
                            }
                        }
                        case LEFT -> {
                            // tournée à gauche
                            Direction newOrientation = switch (current.orientation()) {
                                case NORTH -> Direction.WEST;
                                case SOUTH -> Direction.EAST;
                                case EAST  -> Direction.NORTH;
                                case WEST  -> Direction.SOUTH;
                            };
                            current = new Position(current.coordinates(), newOrientation);
                        }
                        case RIGHT -> {
                            // tournée à droite
                            Direction newOrientation = switch (current.orientation()) {
                                case NORTH -> Direction.EAST;
                                case SOUTH -> Direction.WEST;
                                case EAST  -> Direction.SOUTH;
                                case WEST  -> Direction.NORTH;
                            };
                            current = new Position(current.coordinates(), newOrientation);
                        }
                    }
                    i++;
            }

            }
            finalStates.add(new MarsRoverState(destroyed, current));
        }
            // Compute percentage explored based on the grid size
            int totalCells = input.grid().width() * input.grid().height();
            double percentageExplored = (explored.size() * 100.0) / totalCells;

        return new MarsRoverOutput(percentageExplored, finalStates);
    }
}
