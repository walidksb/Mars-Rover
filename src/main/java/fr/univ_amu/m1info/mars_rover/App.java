package fr.univ_amu.m1info.mars_rover;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
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

                //Simulation
                Coordinates c1 = new Coordinates(1, 3);
                Position p1 = new Position(c1, Direction.NORTH);
                MarsRoverState state1 = new MarsRoverState(false, p1);

                Coordinates c2 = new Coordinates(4, 3);
                Position p2 = new Position(c2, Direction.EAST);
                MarsRoverState state2 = new MarsRoverState(false, p2);

                final MarsRoverOutput marsRoverOutput = new MarsRoverOutput(28.0, List.of(state1, state2));

                // Écriture du fichier output.yml
                ObjectWriter writer = objectMapper.writer();
                try (FileOutputStream fos = new FileOutputStream("output.yml");
                     SequenceWriter sw = writer.writeValues(fos)) {
                     sw.write(marsRoverOutput);
                }

                System.out.println("Fichier 'output.yml' genere avec succes !");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}