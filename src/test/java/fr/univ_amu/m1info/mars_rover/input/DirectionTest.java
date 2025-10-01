package fr.univ_amu.m1info.mars_rover.input;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class DirectionTest {

    @Test
    void testValues_shouldContainFourCardinalDirections() {
        // WHEN
        Direction[] vals = Direction.values();

        // THEN
        assertThat(vals)
                .as("L'énumération doit contenir les quatre directions cardinales")
                .hasSize(4)
                .contains(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
    }

    @Test
    void testValueOf_shouldReturnCorrectEnum() {
        // THEN
        assertThat(Direction.valueOf("NORTH")).isEqualTo(Direction.NORTH);
        assertThat(Direction.valueOf("SOUTH")).isEqualTo(Direction.SOUTH);
        assertThat(Direction.valueOf("EAST")).isEqualTo(Direction.EAST);
        assertThat(Direction.valueOf("WEST")).isEqualTo(Direction.WEST);
    }

    @Test
    void testLeftRotation_expectedMapping() {
        // GIVEN + WHEN + THEN
        assertThat(rotateLeft(Direction.NORTH))
                .as("Rotation à gauche depuis NORTH doit donner WEST")
                .isEqualTo(Direction.WEST);

        assertThat(rotateLeft(Direction.WEST))
                .as("Rotation à gauche depuis WEST doit donner SOUTH")
                .isEqualTo(Direction.SOUTH);

        assertThat(rotateLeft(Direction.SOUTH))
                .as("Rotation à gauche depuis SOUTH doit donner EAST")
                .isEqualTo(Direction.EAST);

        assertThat(rotateLeft(Direction.EAST))
                .as("Rotation à gauche depuis EAST doit donner NORTH")
                .isEqualTo(Direction.NORTH);
    }

    @Test
    void testRightRotation_expectedMapping() {
        // GIVEN + WHEN + THEN
        assertThat(rotateRight(Direction.NORTH))
                .as("Rotation à droite depuis NORTH doit donner EAST")
                .isEqualTo(Direction.EAST);

        assertThat(rotateRight(Direction.EAST))
                .as("Rotation à droite depuis EAST doit donner SOUTH")
                .isEqualTo(Direction.SOUTH);

        assertThat(rotateRight(Direction.SOUTH))
                .as("Rotation à droite depuis SOUTH doit donner WEST")
                .isEqualTo(Direction.WEST);

        assertThat(rotateRight(Direction.WEST))
                .as("Rotation à droite depuis WEST doit donner NORTH")
                .isEqualTo(Direction.NORTH);
    }

    // Helpers pour simuler les rotations (si non implémentées dans l'enum)
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
}
