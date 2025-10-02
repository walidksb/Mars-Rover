package fr.univ_amu.m1info.mars_rover.output;

import java.util.List;
import java.util.Set;

public record MarsRoverOutput(double percentageExplored, List<MarsRoverState> finalRoverStates, Set<fr.univ_amu.m1info.mars_rover.input.Coordinates> exploredCells) {}
