package fr.univ_amu.m1info.mars_rover.output;

import fr.univ_amu.m1info.mars_rover.input.Coordinates;
import fr.univ_amu.m1info.mars_rover.input.Direction;
import fr.univ_amu.m1info.mars_rover.input.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MarsRoverOutputTest {

    @Test
    void testConstructorAndGetters_shouldReturnExpectedValues() {
        // GIVEN – création d’états simulés
        Coordinates c1 = new Coordinates(1, 3);
        Position p1 = new Position(c1, Direction.NORTH);
        MarsRoverState s1 = new MarsRoverState(false, p1);

        Coordinates c2 = new Coordinates(4, 3);
        Position p2 = new Position(c2, Direction.EAST);
        MarsRoverState s2 = new MarsRoverState(false, p2);

        double percentage = 28.0;
        List<MarsRoverState> states = List.of(s1, s2);

        // WHEN
        MarsRoverOutput output = new MarsRoverOutput(percentage, states);

        // THEN
        assertThat(output.percentageExplored())
                .as("Le pourcentage exploré doit correspondre à la valeur fournie")
                .isEqualTo(28.0);

        assertThat(output.finalRoverStates())
                .as("La liste des états finaux doit contenir les deux rovers simulés")
                .hasSize(2);

        assertThat(output.finalRoverStates().get(0).position().orientation())
                .as("Le premier rover doit être orienté vers le Nord")
                .isEqualTo(Direction.NORTH);

        assertThat(output.finalRoverStates().get(1).position().orientation())
                .as("Le second rover doit être orienté vers l'Est")
                .isEqualTo(Direction.EAST);
    }

    @Test
    void testOutputShouldHandleEmptyList() {
        // GIVEN
        MarsRoverOutput output = new MarsRoverOutput(0.0, List.of());

        // THEN
        assertThat(output.percentageExplored())
                .as("Le pourcentage exploré doit être 0.0 pour un scénario vide")
                .isZero();

        assertThat(output.finalRoverStates())
                .as("La liste d'états doit être vide s'il n'y a aucun rover")
                .isEmpty();
    }

    @Test
    void testOutputPercentageCannotBeNegative() {
        // GIVEN
        MarsRoverOutput output = new MarsRoverOutput(-10.0, List.of());

        // THEN
        assertThat(output.percentageExplored())
                .as("Le pourcentage ne devrait jamais être négatif dans une utilisation normale")
                .isNegative();
    }

    @Test
    void testToStringShouldContainPercentageAndStates() {
        // GIVEN
        Coordinates c = new Coordinates(2, 2);
        Position p = new Position(c, Direction.SOUTH);
        MarsRoverState s = new MarsRoverState(false, p);
        MarsRoverOutput output = new MarsRoverOutput(50.0, List.of(s));

        // WHEN
        String text = output.toString();

        // THEN
        assertThat(text)
                .as("Le toString() doit contenir le pourcentage exploré et l'orientation")
                .contains("percentageExplored", "50.0", "SOUTH");
    }
}
