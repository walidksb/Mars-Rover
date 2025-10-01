package fr.univ_amu.m1info.mars_rover.input;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class PositionTest {

    @Test
    void testConstructor_shouldStoreCoordinatesAndOrientation() {
        // GIVEN
        Coordinates c = new Coordinates(2, 3);

        // WHEN
        Position p = new Position(c, Direction.NORTH);

        // THEN
        assertThat(p.coordinates().x())
                .as("La coordonnée X doit être stockée correctement")
                .isEqualTo(2);

        assertThat(p.coordinates().y())
                .as("La coordonnée Y doit être stockée correctement")
                .isEqualTo(3);

        assertThat(p.orientation())
                .as("L’orientation doit correspondre à la direction initiale")
                .isEqualTo(Direction.NORTH);
    }

    @Test
    void testEquality_shouldBeBasedOnFields() {
        // GIVEN
        Position p1 = new Position(new Coordinates(1, 1), Direction.EAST);
        Position p2 = new Position(new Coordinates(1, 1), Direction.EAST);
        Position p3 = new Position(new Coordinates(1, 2), Direction.EAST);
        Position p4 = new Position(new Coordinates(1, 1), Direction.NORTH);

        // THEN
        assertThat(p1)
                .as("Deux positions identiques doivent être égales")
                .isEqualTo(p2);

        assertThat(p1.hashCode())
                .as("Deux positions égales doivent avoir le même hashCode")
                .isEqualTo(p2.hashCode());

        assertThat(p1)
                .as("Deux positions avec des coordonnées différentes ne doivent pas être égales")
                .isNotEqualTo(p3);

        assertThat(p1)
                .as("Deux positions avec une orientation différente ne doivent pas être égales")
                .isNotEqualTo(p4);
    }

    @Test
    void testToString_shouldContainCoordinatesAndOrientation() {
        // GIVEN
        Position p = new Position(new Coordinates(0, 0), Direction.SOUTH);

        // WHEN
        String s = p.toString();

        // THEN
        assertThat(s)
                .as("Le toString() doit contenir les coordonnées et l’orientation")
                .contains("Coordinates")
                .contains("0")
                .contains("SOUTH");
    }
}
