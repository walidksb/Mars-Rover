package fr.univ_amu.m1info.mars_rover.output;

import fr.univ_amu.m1info.mars_rover.input.Coordinates;
import fr.univ_amu.m1info.mars_rover.input.Direction;
import fr.univ_amu.m1info.mars_rover.input.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarsRoverOutputTest {

    @Test
    void testConstructorAndGetters_shouldReturnExpectedValues() {
        // Arrange : création d’un état final simulé
        Coordinates c1 = new Coordinates(1, 3);
        Position p1 = new Position(c1, Direction.NORTH);
        MarsRoverState s1 = new MarsRoverState(false, p1);

        Coordinates c2 = new Coordinates(4, 3);
        Position p2 = new Position(c2, Direction.EAST);
        MarsRoverState s2 = new MarsRoverState(false, p2);

        double percentage = 28.0;
        List<MarsRoverState> states = List.of(s1, s2);

        // Act : création de l’objet MarsRoverOutput
        MarsRoverOutput output = new MarsRoverOutput(percentage, states);

        // Assert : vérifier que les valeurs sont bien enregistrées
        assertEquals(28.0, output.percentageExplored());
        assertEquals(2, output.finalRoverStates().size());
        assertEquals(Direction.NORTH, output.finalRoverStates().get(0).position().orientation());
        assertEquals(Direction.EAST, output.finalRoverStates().get(1).position().orientation());
    }

    @Test
    void testOutputShouldHandleEmptyList() {
        // Arrange : aucun rover
        MarsRoverOutput output = new MarsRoverOutput(0.0, List.of());

        // Assert : vérifier les valeurs par défaut
        assertEquals(0.0, output.percentageExplored());
        assertTrue(output.finalRoverStates().isEmpty());
    }

    @Test
    void testOutputPercentageCannotBeNegative() {
        // Arrange : tentative de création d’un pourcentage négatif
        MarsRoverOutput output = new MarsRoverOutput(-10.0, List.of());

        // Assert : même si techniquement possible, la valeur ne devrait pas être utilisée
        assertTrue(output.percentageExplored() < 0,
                "Le pourcentage négatif devrait être évité dans la logique métier");
    }

    @Test
    void testToStringShouldContainPercentageAndStates() {
        // Arrange
        Coordinates c = new Coordinates(2, 2);
        Position p = new Position(c, Direction.SOUTH);
        MarsRoverState s = new MarsRoverState(false, p);
        MarsRoverOutput output = new MarsRoverOutput(50.0, List.of(s));

        // Act
        String text = output.toString();

        // Assert : vérifier que la chaîne contient bien les informations principales
        assertTrue(text.contains("percentageExplored"));
        assertTrue(text.contains("50.0"));
        assertTrue(text.contains("SOUTH"));
    }
}
