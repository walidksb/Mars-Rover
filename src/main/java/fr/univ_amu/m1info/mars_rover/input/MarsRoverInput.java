package fr.univ_amu.m1info.mars_rover.input;

import java.util.List;
import java.util.Set;

public record MarsRoverInput(GridConfiguration grid, List<RoverConfiguration> rovers, Set<Coordinates> obstacles) {

}