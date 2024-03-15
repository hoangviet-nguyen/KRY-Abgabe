package fileio;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class File {
    private static final Logger LOGGER = Logger.getLogger(File.class.getName());
    public String readFile(String filePath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "An IO exception occurred while reading the file.", e);
        }
        return content;
    }
}