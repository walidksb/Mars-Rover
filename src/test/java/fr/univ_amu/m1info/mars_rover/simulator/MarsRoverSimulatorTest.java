package fr.univ_amu.m1info.mars_rover.simulator;

import fr.univ_amu.m1info.mars_rover.input.*;
import fr.univ_amu.m1info.mars_rover.output.MarsRoverOutput;
import fr.univ_amu.m1info.mars_rover.output.MarsRoverState;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarsRoverSimulatorTest {

    // --- Cas normal : un rover avance vers le nord
    @Test
    void testMove_whenFacingNorth_shouldIncreaseY() {
        GridConfiguration grid = new GridConfiguration(5, 5, GridKind.RECTANGULAR);
        Position position = new Position(new Coordinates(2, 2), Direction.NORTH);
        RoverConfiguration rover = new RoverConfiguration(position, List.of(Command.MOVE));

        MarsRoverSimulator simulator = new MarsRoverSimulator();
        MarsRoverInput input = new MarsRoverInput(grid, List.of(rover));

        MarsRoverOutput output = simulator.simulate(input);

        MarsRoverState finalState = output.finalRoverStates().get(0);
        assertEquals(2, finalState.position().coordinates().x());
        assertEquals(3, finalState.position().coordinates().y(), "Y doit augmenter de 1 quand on avance vers le nord");
    }

    // --- Cas normal : tourner à gauche et à droite
    @Test
    void testTurnLeftAndRight_shouldChangeOrientation() {
        GridConfiguration grid = new GridConfiguration(5, 5, GridKind.RECTANGULAR);
        Position position = new Position(new Coordinates(1, 1), Direction.NORTH);
        RoverConfiguration rover = new RoverConfiguration(position, List.of(Command.LEFT, Command.RIGHT));

        MarsRoverSimulator simulator = new MarsRoverSimulator();
        MarsRoverInput input = new MarsRoverInput(grid, List.of(rover));

        MarsRoverOutput output = simulator.simulate(input);
        MarsRoverState finalState = output.finalRoverStates().get(0);

        assertEquals(Direction.NORTH, finalState.position().orientation(),
                "Après un LEFT puis RIGHT, la direction doit rester NORTH");
    }

    // --- Cas limite : rover au bord qui avance vers l’extérieur
    @Test
    void testMove_whenAtGridBoundary_shouldBeDestroyed() {
        GridConfiguration grid = new GridConfiguration(5, 5, GridKind.RECTANGULAR);
        Position position = new Position(new Coordinates(0, 0), Direction.SOUTH);
        RoverConfiguration rover = new RoverConfiguration(position, List.of(Command.MOVE));

        MarsRoverSimulator simulator = new MarsRoverSimulator();
        MarsRoverInput input = new MarsRoverInput(grid, List.of(rover));

        MarsRoverOutput output = simulator.simulate(input);
        MarsRoverState state = output.finalRoverStates().get(0);

        assertTrue(state.isDestroyed(), "Le rover doit être détruit s’il sort de la grille");
    }

    // --- Cas limite : aucun rover dans la simulation
    @Test
    void testSimulate_whenNoRovers_shouldReturnEmptyOutput() {
        GridConfiguration grid = new GridConfiguration(5, 5, GridKind.RECTANGULAR);
        MarsRoverInput input = new MarsRoverInput(grid, List.of());

        MarsRoverSimulator simulator = new MarsRoverSimulator();
        MarsRoverOutput output = simulator.simulate(input);

        assertTrue(output.finalRoverStates().isEmpty(), "La liste des états doit être vide");
        assertEquals(0.0, output.percentageExplored(), "Rien ne doit être exploré");
    }

    // --- Cas anormal : commande inconnue (si la logique la gère)
    @Test
    void testSimulate_whenInvalidCommand_shouldHandleGracefully() {
        GridConfiguration grid = new GridConfiguration(5, 5, GridKind.RECTANGULAR);
        Position position = new Position(new Coordinates(1, 1), Direction.NORTH);

        // Si ton code ignore ou lève une exception pour les commandes inconnues
        List<Command> commands = List.of(); // vide ou fausse commande simulée
        RoverConfiguration rover = new RoverConfiguration(position, commands);
        MarsRoverInput input = new MarsRoverInput(grid, List.of(rover));

        MarsRoverSimulator simulator = new MarsRoverSimulator();
        MarsRoverOutput output = simulator.simulate(input);

        assertEquals(1, output.finalRoverStates().size());
        assertEquals(position, output.finalRoverStates().get(0).position(),
                "Sans commandes valides, la position doit rester identique");
    }

    // --- Cas normal : plusieurs rovers
    @Test
    void testSimulate_whenTwoRovers_shouldProduceTwoStates() {
        GridConfiguration grid = new GridConfiguration(5, 5, GridKind.RECTANGULAR);

        Position p1 = new Position(new Coordinates(1, 2), Direction.NORTH);
        RoverConfiguration r1 = new RoverConfiguration(p1, List.of(Command.MOVE));

        Position p2 = new Position(new Coordinates(3, 3), Direction.EAST);
        RoverConfiguration r2 = new RoverConfiguration(p2, List.of(Command.MOVE));

        MarsRoverSimulator simulator = new MarsRoverSimulator();
        MarsRoverInput input = new MarsRoverInput(grid, List.of(r1, r2));

        MarsRoverOutput output = simulator.simulate(input);

        assertEquals(2, output.finalRoverStates().size(), "Deux rovers en entrée => deux états finaux");
    }
}
