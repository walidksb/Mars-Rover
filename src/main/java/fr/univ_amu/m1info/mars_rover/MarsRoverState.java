package fr.univ_amu.m1info.mars_rover;

public class MarsRoverState {
    boolean isDestroyed;
    Position position;
    // Constructor
    public MarsRoverState(boolean isDestroyed, Position position) {
        this.isDestroyed = isDestroyed;
        this.position = position;
    }

    // Getters and setters
    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "MarsRoverState{" +
                "isDestroyed=" + isDestroyed +
                ", position=" + position +
                '}';
    }
}
