package fr.univ_amu.m1info.mars_rover.input;

import java.util.List;
import java.util.Objects;

public record RoverConfiguration(Position position, List<Command> commands) {
    public RoverConfiguration {
        // Vérifier que la position n'est pas nulle
        Objects.requireNonNull(position, "La position ne doit pas être null");

        // Vérifier que la liste des commandes n'est pas nulle
        Objects.requireNonNull(commands, "La liste de commandes ne doit pas être null");
    }
}