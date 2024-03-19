package fileio;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

// This class provides a method to read a file and return its content as a string.
public class File {
    // Logger for logging any exceptions that occur while reading the file
    private static final Logger LOGGER = Logger.getLogger(File.class.getName());

    // Method to read a file and return its content as a string
    public String readFile(String filePath) {
        String content = "";
        try {
            // Read all bytes from the file at the given path and convert them to a string
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            // Log a severe message if an IO exception occurs while reading the file
            LOGGER.log(Level.SEVERE, "An IO exception occurred while reading the file.", e);
        }
        // Return the content of the file
        return content;
    }
}