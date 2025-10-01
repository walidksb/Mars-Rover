package fr.univ_amu.m1info.mars_rover.output;

import fr.univ_amu.m1info.mars_rover.input.Position;

public record MarsRoverState(boolean isDestroyed, Position position) {}