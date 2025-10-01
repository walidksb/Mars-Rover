package fr.univ_amu.m1info.mars_rover.input;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CoordinatesTest {

    @Test
    void testCoordinatesInitialization_shouldStoreValuesCorrectly() {
        // GIVEN
        int x = 3;
        int y = 5;

        // WHEN
        Coordinates coordinates = new Coordinates(x, y);

        // THEN
        assertThat(coordinates.x())
                .as("La coordonnée X doit être égale à la valeur fournie")
                .isEqualTo(x);

        assertThat(coordinates.y())
                .as("La coordonnée Y doit être égale à la valeur fournie")
                .isEqualTo(y);
    }

    @Test
    void testCoordinatesEquality_shouldReturnTrueForSameValues() {
        // GIVEN
        Coordinates c1 = new Coordinates(2, 4);
        Coordinates c2 = new Coordinates(2, 4);

        // THEN
        assertThat(c1)
                .as("Deux objets Coordinates avec les mêmes valeurs doivent être égaux")
                .isEqualTo(c2);
    }

    @Test
    void testCoordinatesInequality_shouldReturnTrueForDifferentValues() {
        // GIVEN
        Coordinates c1 = new Coordinates(2, 4);
        Coordinates c2 = new Coordinates(3, 5);

        // THEN
        assertThat(c1)
                .as("Deux objets Coordinates avec des valeurs différentes ne doivent pas être égaux")
                .isNotEqualTo(c2);
    }

    @Test
    void testToString_shouldContainXandYValues() {
        // GIVEN
        Coordinates c = new Coordinates(1, 2);

        // WHEN
        String result = c.toString();

        // THEN
        assertThat(result)
                .as("Le toString() doit contenir les coordonnées x et y")
                .contains("1")
                .contains("2");
    }
}
