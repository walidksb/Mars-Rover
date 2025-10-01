package fr.univ_amu.m1info.mars_rover.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void testValues_shouldContainFourCardinalDirections() {
        Direction[] vals = Direction.values();
        assertEquals(4, vals.length);
        // ordre non garanti contractuellement, on vérifie la présence
        assertTrue(contains(vals, Direction.NORTH));
        assertTrue(contains(vals, Direction.SOUTH));
        assertTrue(contains(vals, Direction.EAST));
        assertTrue(contains(vals, Direction.WEST));
    }

    @Test
    void testValueOf_shouldReturnCorrectEnum() {
        assertEquals(Direction.NORTH, Direction.valueOf("NORTH"));
        assertEquals(Direction.SOUTH, Direction.valueOf("SOUTH"));
        assertEquals(Direction.EAST, Direction.valueOf("EAST"));
        assertEquals(Direction.WEST, Direction.valueOf("WEST"));
    }

    // --- Tests bonus : table de rotation attendue (utile pour documenter le comportement)
    @Test
    void testLeftRotation_expectedMapping() {
        assertEquals(Direction.WEST,  rotateLeft(Direction.NORTH));
        assertEquals(Direction.SOUTH, rotateLeft(Direction.WEST));
        assertEquals(Direction.EAST,  rotateLeft(Direction.SOUTH));
        assertEquals(Direction.NORTH, rotateLeft(Direction.EAST));
    }

    @Test
    void testRightRotation_expectedMapping() {
        assertEquals(Direction.EAST,  rotateRight(Direction.NORTH));
        assertEquals(Direction.NORTH, rotateRight(Direction.WEST));
        assertEquals(Direction.WEST,  rotateRight(Direction.SOUTH));
        assertEquals(Direction.SOUTH, rotateRight(Direction.EAST));
    }

    // Helpers pour les tests (si tu n'as pas de méthodes left()/right() dans l'enum)
    private static Direction rotateLeft(Direction d) {
        return switch (d) {
            case NORTH -> Direction.WEST;
            case WEST  -> Direction.SOUTH;
            case SOUTH -> Direction.EAST;
            case EAST  -> Direction.NORTH;
        };
    }

    private static Direction rotateRight(Direction d) {
        return switch (d) {
            case NORTH -> Direction.EAST;
            case EAST  -> Direction.SOUTH;
            case SOUTH -> Direction.WEST;
            case WEST  -> Direction.NORTH;
        };
    }

    private static boolean contains(Direction[] arr, Direction target) {
        for (Direction d : arr) if (d == target) return true;
        return false;
    }
}
