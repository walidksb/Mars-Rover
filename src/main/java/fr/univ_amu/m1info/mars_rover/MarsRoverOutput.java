package fr.univ_amu.m1info.mars_rover;

import java.util.List;

public record MarsRoverOutput(double percentageExplored, List<MarsRoverState> finalRoverStates) {}
