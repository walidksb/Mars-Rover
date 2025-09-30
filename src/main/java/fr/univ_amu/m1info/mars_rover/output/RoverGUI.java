package fr.univ_amu.m1info.mars_rover.output;

import fr.univ_amu.m1info.mars_rover.input.Coordinates;
import fr.univ_amu.m1info.mars_rover.input.Direction;
import fr.univ_amu.m1info.mars_rover.input.Position;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class RoverGUI extends JPanel {

    private final int gridWidth;
    private final int gridHeight;
    private int cellSize = 20;

    // Utilisation de collections thread-safe pour les mises à jour depuis un autre thread
    private final List<Position> currentRoverPositions = new CopyOnWriteArrayList<>();
    private final Set<Coordinates> exploredCells = new CopyOnWriteArraySet<>();
    private List<MarsRoverState> finalRoverStates = new CopyOnWriteArrayList<>();

    public RoverGUI(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        int frameWidth = gridWidth * cellSize + 300;
        int frameHeight = gridHeight * cellSize + 300;
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setBackground(new Color(210, 180, 140));
    }

    // Met à jour l'état de la simulation pour l'affichage.
    public void updateSimulationState(List<Position> positions, Set<Coordinates> explored) {
        this.currentRoverPositions.clear();
        this.currentRoverPositions.addAll(positions);
        this.exploredCells.clear();
        this.exploredCells.addAll(explored);
        this.finalRoverStates.clear();
        repaint();
    }


    // Affiche l'état final, notamment les rovers détruits.
    public void setFinalState(List<MarsRoverState> finalStates, Set<Coordinates> explored) {
        this.currentRoverPositions.clear();
        this.finalRoverStates.clear();
        this.finalRoverStates.addAll(finalStates);
        this.exploredCells.clear();
        this.exploredCells.addAll(explored);
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Calcul dynamique de la taille des cellules pour s'adapter à la fenêtre
        int width = getWidth();
        int height = getHeight();
        this.cellSize = Math.min((width - 40) / gridWidth, (height - 40) / gridHeight);
        int offsetX = (width - gridWidth * cellSize) / 2;
        int offsetY = (height - gridHeight * cellSize) / 2;

        // Dessiner les cellules explorées
        g2d.setColor(new Color(170, 140, 100));
        for (Coordinates cell : exploredCells) {
            int x = offsetX + cell.x() * cellSize;
            int y = offsetY + (gridHeight - 1 - cell.y()) * cellSize;
            g2d.fillRect(x, y, cellSize, cellSize);
        }

        // Dessiner la grille
        g2d.setColor(Color.DARK_GRAY);
        for (int i = 0; i <= gridWidth; i++) {
            g2d.drawLine(offsetX + i * cellSize, offsetY, offsetX + i * cellSize, offsetY + gridHeight * cellSize);
        }
        for (int i = 0; i <= gridHeight; i++) {
            g2d.drawLine(offsetX, offsetY + i * cellSize, offsetX + gridWidth * cellSize, offsetY + i * cellSize);
        }

        // Dessiner les rovers en cours d'animation
        for (Position pos : currentRoverPositions) {
            drawRover(g2d, pos, Color.BLUE);
        }

        // Dessiner les rovers à leur état final
        for (MarsRoverState state : finalRoverStates) {
            if (state.isDestroyed()) {
                drawDestroyedRover(g2d, state.getPosition());
            } else {
                drawRover(g2d, state.getPosition(), new Color(0, 100, 0));
            }
        }
    }

    private void drawRover(Graphics2D g2d, Position pos, Color color) {
        if (pos == null) return;

        int centerX = (getWidth() - gridWidth * cellSize) / 2 + pos.coordinates().x() * cellSize + cellSize / 2;
        int centerY = (getHeight() - gridHeight * cellSize) / 2 + (gridHeight - 1 - pos.coordinates().y()) * cellSize + cellSize / 2;
        int size = (int) (cellSize * 0.8);

        g2d.setColor(color);
        g2d.fillOval(centerX - size / 2, centerY - size / 2, size, size);

        // Dessiner un indicateur de direction
        g2d.setColor(Color.WHITE);
        int endX = centerX;
        int endY = centerY;
        switch (pos.orientation()) {
            case NORTH -> endY -= size / 2;
            case SOUTH -> endY += size / 2;
            case EAST -> endX += size / 2;
            case WEST -> endX -= size / 2;
        }
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(centerX, centerY, endX, endY);
    }

    private void drawDestroyedRover(Graphics2D g2d, Position pos) {
        if (pos == null) return;

        int x = (getWidth() - gridWidth * cellSize) / 2 + pos.coordinates().x() * cellSize;
        int y = (getHeight() - gridHeight * cellSize) / 2 + (gridHeight - 1 - pos.coordinates().y()) * cellSize;
        int margin = (int) (cellSize * 0.2);

        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(x + margin, y + margin, x + cellSize - margin, y + cellSize - margin);
        g2d.drawLine(x + margin, y + cellSize - margin, x + cellSize - margin, y + margin);
    }
}
