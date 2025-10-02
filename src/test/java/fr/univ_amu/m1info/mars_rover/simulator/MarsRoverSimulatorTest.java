package fr.univ_amu.m1info.mars_rover.simulator;

import fr.univ_amu.m1info.mars_rover.input.*;
import fr.univ_amu.m1info.mars_rover.output.MarsRoverOutput;
import fr.univ_amu.m1info.mars_rover.output.MarsRoverState;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MarsRoverSimulatorTest {

    // --- Cas normal : un rover avance vers le nord
    @Test
    void testMove_whenFacingNorth_shouldIncreaseY() {
        // GIVEN
        GridConfiguration grid = new GridConfiguration(5, 5, GridKind.RECTANGULAR);
        Position position = new Position(new Coordinates(2, 2), Direction.NORTH);
        RoverConfiguration rover = new RoverConfiguration(position, List.of(Command.MOVE));

        MarsRoverSimulator simulator = new MarsRoverSimulator();
        MarsRoverInput input = new MarsRoverInput(grid, List.of(rover), Collections.emptySet());

        // WHEN
        MarsRoverOutput output = simulator.simulate(input);

        // THEN
        MarsRoverState finalState = output.finalRoverStates().get(0);
        assertThat(finalState.position().coordinates().x())
                .as("La coordonnée X ne doit pas changer en avançant vers le nord")
                .isEqualTo(2);

        assertThat(finalState.position().coordinates().y())
                .as("La coordonnée Y doit augmenter de 1 quand on avance vers le nord")
                .isEqualTo(3);
    }

    // --- Cas normal : tourner à gauche et à droite
    @Test
    void testTurnLeftAndRight_shouldChangeOrientation() {
        // GIVEN
        GridConfiguration grid = new GridConfiguration(5, 5, GridKind.RECTANGULAR);
        Position position = new Position(new Coordinates(1, 1), Direction.NORTH);
        RoverConfiguration rover = new RoverConfiguration(position, List.of(Command.LEFT, Command.RIGHT));

        MarsRoverSimulator simulator = new MarsRoverSimulator();
        MarsRoverInput input = new MarsRoverInput(grid, List.of(rover), Collections.emptySet());

        // WHEN
        MarsRoverOutput output = simulator.simulate(input);

        // THEN
        MarsRoverState finalState = output.finalRoverStates().get(0);
        assertThat(finalState.position().orientation())
                .as("Après un LEFT puis un RIGHT, la direction doit rester NORTH")
                .isEqualTo(Direction.NORTH);
    }

    // --- Cas limite : rover au bord qui avance vers l’extérieur
    @Test
    void testMove_whenAtGridBoundary_shouldBeDestroyed() {
        // GIVEN
        GridConfiguration grid = new GridConfiguration(5, 5, GridKind.RECTANGULAR);
        Position position = new Position(new Coordinates(0, 0), Direction.SOUTH);
        RoverConfiguration rover = new RoverConfiguration(position, List.of(Command.MOVE));

        MarsRoverSimulator simulator = new MarsRoverSimulator();
        MarsRoverInput input = new MarsRoverInput(grid, List.of(rover), Collections.emptySet());

        // WHEN
        MarsRoverOutput output = simulator.simulate(input);

        // THEN
        MarsRoverState state = output.finalRoverStates().get(0);
        assertThat(state.isDestroyed())
                .as("Le rover doit être détruit s’il sort de la grille")
                .isTrue();
    }

    // --- Cas limite : aucun rover dans la simulation
    @Test
    void testSimulate_whenNoRovers_shouldReturnEmptyOutput() {
        // GIVEN
        GridConfiguration grid = new GridConfiguration(5, 5, GridKind.RECTANGULAR);
        MarsRoverInput input = new MarsRoverInput(grid, List.of(), Collections.emptySet());

        MarsRoverSimulator simulator = new MarsRoverSimulator();

        // WHEN
        MarsRoverOutput output = simulator.simulate(input);

        // THEN
        assertThat(output.finalRoverStates())
                .as("La liste des états doit être vide s'il n'y a aucun rover")
                .isEmpty();

        assertThat(output.percentageExplored())
                .as("Le pourcentage exploré doit être nul s'il n'y a aucune exploration")
                .isZero();
    }

    // --- Cas anormal : commande inconnue ou vide
    @Test
    void testSimulate_whenInvalidCommand_shouldHandleGracefully() {
        // GIVEN
        GridConfiguration grid = new GridConfiguration(5, 5, GridKind.RECTANGULAR);
        Position position = new Position(new Coordinates(1, 1), Direction.NORTH);
        List<Command> commands = List.of(); // Liste vide (aucune commande)
        RoverConfiguration rover = new RoverConfiguration(position, commands);

        MarsRoverSimulator simulator = new MarsRoverSimulator();
        MarsRoverInput input = new MarsRoverInput(grid, List.of(rover), Collections.emptySet());

        // WHEN
        MarsRoverOutput output = simulator.simulate(input);

        // THEN
        assertThat(output.finalRoverStates())
                .as("Il doit y avoir un état final pour le rover même sans commande")
                .hasSize(1);

        assertThat(output.finalRoverStates().get(0).position())
                .as("Sans commandes valides, la position doit rester identique")
                .isEqualTo(position);
    }

    // --- Cas normal : plusieurs rovers
    @Test
    void testSimulate_whenTwoRovers_shouldProduceTwoStates() {
        // GIVEN
        GridConfiguration grid = new GridConfiguration(5, 5, GridKind.RECTANGULAR);

        Position p1 = new Position(new Coordinates(1, 2), Direction.NORTH);
        RoverConfiguration r1 = new RoverConfiguration(p1, List.of(Command.MOVE));

        Position p2 = new Position(new Coordinates(3, 3), Direction.EAST);
        RoverConfiguration r2 = new RoverConfiguration(p2, List.of(Command.MOVE));

        MarsRoverSimulator simulator = new MarsRoverSimulator();
        MarsRoverInput input = new MarsRoverInput(grid, List.of(r1, r2), Collections.emptySet());

        // WHEN
        MarsRoverOutput output = simulator.simulate(input);

        // THEN
        assertThat(output.finalRoverStates())
                .as("Deux rovers en entrée doivent produire deux états finaux")
                .hasSize(2);
    }
}
