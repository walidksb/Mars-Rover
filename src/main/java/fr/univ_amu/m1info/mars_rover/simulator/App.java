package fr.univ_amu.m1info.mars_rover.simulator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import fr.univ_amu.m1info.mars_rover.input.MarsRoverInput;
import fr.univ_amu.m1info.mars_rover.output.MarsRoverOutput;


public class App {
        public static void main(String[] args) {
            final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory()
                    .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
                    .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
            try {
                final InputStream inputStream
                        = App.class.getResourceAsStream("/config.yml");
                final MarsRoverInput marsRoverMarsRoverInput =
                        objectMapper.readValue(inputStream, MarsRoverInput.class);
                System.out.println("Configuration chargée !");
                System.out.println(marsRoverMarsRoverInput);

                MarsRoverSimulator simulator = new MarsRoverSimulator();
                /*
                //Simulation
                Coordinates c1 = new Coordinates(1, 3);
                Position p1 = new Position(c1, Direction.NORTH);
                MarsRoverState state1 = new MarsRoverState(false, p1);

                Coordinates c2 = new Coordinates(4, 3);
                Position p2 = new Position(c2, Direction.EAST);
                MarsRoverState state2 = new MarsRoverState(false, p2);

                final MarsRoverOutput marsRoverOutput = new MarsRoverOutput(28.0, List.of(state1, state2));
                */
                MarsRoverOutput marsRoverOutput = simulator.simulate(marsRoverMarsRoverInput);
                // Écriture du fichier output.yml
                ObjectWriter writer = objectMapper.writer();
                try (FileOutputStream fos = new FileOutputStream("FinalOutput.yml");
                     SequenceWriter sw = writer.writeValues(fos)) {
                     sw.write(marsRoverOutput);
                }

                //System.out.println("Fichier 'output.yml' genere avec succes !");
                System.out.println("Simulation terminer !");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}