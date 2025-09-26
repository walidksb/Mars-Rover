package fr.univ_amu.m1info.mars_rover;

import java.util.List;

public class MarsRoverOutput {
    double percentageExplored;
    List<MarsRoverState> finalRoverStates;
    // Constructor
    public MarsRoverOutput(double percentageExplored, List<MarsRoverState> finalRoverStates) {
        this.percentageExplored = percentageExplored;
        this.finalRoverStates = finalRoverStates;
    }

    // Getters and setters
    public double getPercentageExplored() {
        return percentageExplored;
    }

    public void setPercentageExplored(double percentageExplored) {
        this.percentageExplored = percentageExplored;
    }

    public List<MarsRoverState> getFinalRoverStates() {
        return finalRoverStates;
    }

    public void setFinalRoverStates(List<MarsRoverState> finalRoverStates) {
        this.finalRoverStates = finalRoverStates;
    }

    @Override
    public String toString() {
        return "MarsRoverOutput{" +
                "percentageExplored=" + percentageExplored +
                ", finalRoverStates=" + finalRoverStates +
                '}';
    }
}
