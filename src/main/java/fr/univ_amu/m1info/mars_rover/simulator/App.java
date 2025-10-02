package fr.univ_amu.m1info.mars_rover.simulator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import fr.univ_amu.m1info.mars_rover.input.MarsRoverInput;
import fr.univ_amu.m1info.mars_rover.output.MarsRoverOutput;
import fr.univ_amu.m1info.mars_rover.output.RoverGUI;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

public class App {

    public static void main(String[] args) {
        try {
            MarsRoverInput input = loadConfig("/config.yml");

            // Création de l'interface graphique
            int gridW = input.grid().width();
            int gridH = input.grid().height();

            JFrame frame = new JFrame("Mars Rover Simulation");
            RoverGUI panel = new RoverGUI(gridW, gridH);
            frame.add(panel);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Création du simulateur
            MarsRoverSimulator simulator = new MarsRoverSimulator();

            // Définition de l'observateur pour mettre à jour l'Interface Graphique
            MarsRoverSimulator.SimulationObserver observer = (positions, explored) -> {
                // Utiliser invokeLater pour garantir que les mises à jour de l'Interface Graphique se font sur le bon thread
                SwingUtilities.invokeLater(() -> panel.updateSimulationState(positions, explored));
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            };

            // Lancement de la simulation en arrière-plan pour ne pas bloquer l'interface
            new Thread(() -> {
                try {
                    MarsRoverOutput output = simulator.simulate(input, observer);
                    writeOutput(output, "FinalOutput.yml");

                    // Affiche l'état final sur l'interface graphique
                    SwingUtilities.invokeLater(() -> panel.setFinalState(
                            output.finalRoverStates(),
                            new HashSet<>()
                    ));
                    System.out.println("Hoho !");
                    System.out.println("Simulation terminer !");
                    System.out.printf("Pourcentage explore : %.2f%%\n", output.percentageExplored());
                    System.out.println("Resultats finaux enregistres dans FinalOutput.yml");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static MarsRoverInput loadConfig(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory()
                .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
                .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        try (InputStream inputStream = App.class.getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IOException("Cannot find resource: " + path);
            }
            MarsRoverInput input = objectMapper.readValue(inputStream, MarsRoverInput.class);
            System.out.println("Configuration chargee !");
            return input;
        }
    }

    private static void writeOutput(MarsRoverOutput output, String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory()
                .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
                .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        ObjectWriter writer = objectMapper.writer();
        try (FileOutputStream fos = new FileOutputStream(path);
             SequenceWriter sw = writer.writeValues(fos)) {
            sw.write(output);
        }
    }
}
