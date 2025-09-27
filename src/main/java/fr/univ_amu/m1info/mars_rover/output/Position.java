package fr.univ_amu.m1info.mars_rover.output;

import fr.univ_amu.m1info.mars_rover.input.Coordinates;
import fr.univ_amu.m1info.mars_rover.input.Direction;

public record Position(Coordinates coordinates, Direction orientation) {

}