package fr.univ_amu.m1info.mars_rover.output;

import java.util.List;

public record MarsRoverOutput(double percentageExplored, List<MarsRoverState> finalRoverStates) {}
