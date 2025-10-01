package fr.univ_amu.m1info.mars_rover.input;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RoverConfigurationTest {

    @Test
    void testRoverConfiguration_shouldStorePositionAndCommands() {
        // Arrange
        Coordinates start = new Coordinates(1, 2);
        Position position = new Position(start, Direction.NORTH);
        List<Command> commands = List.of(Command.LEFT, Command.MOVE, Command.RIGHT);

        // Act
        RoverConfiguration rover = new RoverConfiguration(position, commands);

        // Assert
        assertEquals(position, rover.position());
        assertEquals(3, rover.commands().size());
        assertEquals(Command.LEFT, rover.commands().get(0));
        assertEquals(Command.MOVE, rover.commands().get(1));
        assertEquals(Command.RIGHT, rover.commands().get(2));
    }

    @Test
    void testRoverConfiguration_whenEmptyCommandList_shouldBeValid() {
        // Arrange
        Position position = new Position(new Coordinates(0, 0), Direction.EAST);
        List<Command> commands = List.of(); // aucune commande

        // Act
        RoverConfiguration rover = new RoverConfiguration(position, commands);

        // Assert
        assertNotNull(rover);
        assertEquals(position, rover.position());
        assertTrue(rover.commands().isEmpty(), "La liste de commandes doit etre vide");
    }

    @Test
    void testRoverConfiguration_whenNullPosition_shouldThrowException() {
        // Arrange
        List<Command> commands = List.of(Command.MOVE);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            new RoverConfiguration(null, commands);
        }, "Une position null ne doit pas etre autorisée");
    }

    @Test
    void testRoverConfiguration_whenNullCommandList_shouldThrowException() {
        // Arrange
        Position position = new Position(new Coordinates(2, 3), Direction.SOUTH);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            new RoverConfiguration(position, null);
        }, "La liste de commandes ne doit pas etre null");
    }
}
