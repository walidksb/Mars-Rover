package fr.univ_amu.m1info.mars_rover.input;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

class RoverConfigurationTest {

    @Test
    void testRoverConfiguration_shouldStorePositionAndCommands() {
        // GIVEN
        Coordinates start = new Coordinates(1, 2);
        Position position = new Position(start, Direction.NORTH);
        List<Command> commands = List.of(Command.LEFT, Command.MOVE, Command.RIGHT);

        // WHEN
        RoverConfiguration rover = new RoverConfiguration(position, commands, 0);

        // THEN
        assertThat(rover.position())
                .as("La position du rover doit être stockée correctement")
                .isEqualTo(position);

        assertThat(rover.commands())
                .as("La liste des commandes doit contenir exactement trois éléments")
                .hasSize(3)
                .containsExactly(Command.LEFT, Command.MOVE, Command.RIGHT);
    }

    @Test
    void testRoverConfiguration_whenEmptyCommandList_shouldBeValid() {
        // GIVEN
        Position position = new Position(new Coordinates(0, 0), Direction.EAST);
        List<Command> commands = List.of(); // aucune commande

        // WHEN
        RoverConfiguration rover = new RoverConfiguration(position, commands, 0);

        // THEN
        assertThat(rover)
                .as("L'objet RoverConfiguration doit être correctement créé")
                .isNotNull();

        assertThat(rover.position())
                .as("La position doit correspondre à celle passée en paramètre")
                .isEqualTo(position);

        assertThat(rover.commands())
                .as("La liste de commandes doit être vide lorsqu’aucune commande n’est passée")
                .isEmpty();
    }

    @Test
    void testRoverConfiguration_whenNullPosition_shouldThrowException() {
        // GIVEN
        List<Command> commands = List.of(Command.MOVE);

        // WHEN + THEN
        assertThatThrownBy(() -> new RoverConfiguration(null, commands, 0))
                .as("Une position null ne doit pas être autorisée")
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void testRoverConfiguration_whenNullCommandList_shouldThrowException() {
        // GIVEN
        Position position = new Position(new Coordinates(2, 3), Direction.SOUTH);

        // WHEN + THEN
        assertThatThrownBy(() -> new RoverConfiguration(position, null, 0))
                .as("La liste de commandes ne doit pas être null")
                .isInstanceOf(NullPointerException.class);
    }
}
