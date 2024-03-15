import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class File {
    public String readFile(String filePath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}