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

        for (RoverConfiguration rover : input.rovers()) {
            Position current = rover.position();
            boolean destroyed = false;

            // Exécution fictive des commandes (à améliorer plus tard)
            for (Command cmd : rover.commands()) {
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
                    }
                    case LEFT -> {
                        // tournée à gauche
                    }
                    case RIGHT -> {
                        // tournée à droite
                    }
                }
            }
            finalStates.add(new MarsRoverState(destroyed, current));
        }

        // Pour l’instant, un pourcentage fictif
        double percentageExplored = 28.0;

        return new MarsRoverOutput(percentageExplored, finalStates);
    }
}
