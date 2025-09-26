package fr.univ_amu.m1info.mars_rover;

import java.io.IOException;
import java.io.InputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}