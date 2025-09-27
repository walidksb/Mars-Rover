package fr.univ_amu.m1info.mars_rover.input;

import java.util.List;

public record RoverConfiguration(Position position, List<Command> commands) {

}