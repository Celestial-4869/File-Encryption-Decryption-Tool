import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
    public static byte[] readFile(String path) throws Exception {
        return Files.readAllBytes(Paths.get(path));
    }

    public static void writeFile(String path, byte[] data) throws Exception {
        Files.write(Paths.get(path), data);
    }
}
