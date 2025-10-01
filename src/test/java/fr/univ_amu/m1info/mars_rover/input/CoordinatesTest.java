package fr.univ_amu.m1info.mars_rover.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {

    @Test
    void testConstructor_shouldStoreXandYValues() {
        Coordinates c = new Coordinates(3, 5);

        assertEquals(3, c.x(), "x doit être correctement initialisé");
        assertEquals(5, c.y(), "y doit être correctement initialisé");
    }

    @Test
    void testEquality_shouldBeBasedOnValues() {
        Coordinates c1 = new Coordinates(2, 4);
        Coordinates c2 = new Coordinates(2, 4);
        Coordinates c3 = new Coordinates(3, 4);

        assertEquals(c1, c2, "Deux coordonnées identiques doivent être égales");
        assertNotEquals(c1, c3, "Des coordonnées différentes ne doivent pas être égales");

        assertEquals(c1.hashCode(), c2.hashCode(), "Les hashCodes doivent correspondre pour des objets égaux");
    }

    @Test
    void testToString_shouldContainXandYValues() {
        Coordinates c = new Coordinates(1, 2);
        String result = c.toString();

        assertTrue(result.contains("x=1") || result.contains("1"),
                "Le toString doit contenir la valeur de x");
        assertTrue(result.contains("y=2") || result.contains("2"),
                "Le toString doit contenir la valeur de y");
    }

    @Test
    void testCoordinates_whenNegativeValues_shouldBeAllowed() {
        Coordinates c = new Coordinates(-1, -5);

        assertEquals(-1, c.x());
        assertEquals(-5, c.y());
    }

    @Test
    void testCoordinates_whenZeroValues_shouldBeValid() {
        Coordinates c = new Coordinates(0, 0);

        assertEquals(0, c.x());
        assertEquals(0, c.y());
    }
}
