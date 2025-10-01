package fr.univ_amu.m1info.mars_rover.input;

import fr.univ_amu.m1info.mars_rover.output.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void testConstructor_shouldStoreCoordinatesAndOrientation() {
        Coordinates c = new Coordinates(2, 3);
        Position p = new Position(c, Direction.NORTH);

        assertEquals(2, p.coordinates().x());
        assertEquals(3, p.coordinates().y());
        assertEquals(Direction.NORTH, p.orientation());
    }

    @Test
    void testEquality_shouldBeBasedOnFields() {
        Position p1 = new Position(new Coordinates(1, 1), Direction.EAST);
        Position p2 = new Position(new Coordinates(1, 1), Direction.EAST);
        Position p3 = new Position(new Coordinates(1, 2), Direction.EAST);
        Position p4 = new Position(new Coordinates(1, 1), Direction.NORTH);

        // Les records implémentent equals/hashCode par valeur
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());

        assertNotEquals(p1, p3);
        assertNotEquals(p1, p4);
    }

    @Test
    void testToString_shouldContainCoordinatesAndOrientation() {
        Position p = new Position(new Coordinates(0, 0), Direction.SOUTH);
        String s = p.toString();

        assertTrue(s.contains("Coordinates"));
        assertTrue(s.contains("0"));
        assertTrue(s.contains("SOUTH"));
    }
}
