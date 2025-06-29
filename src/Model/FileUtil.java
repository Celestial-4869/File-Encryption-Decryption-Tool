package Model;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    public static byte[] readBytes(String path) throws Exception {
        return Files.readAllBytes(Paths.get(path));
    }

    public static void writeBytes(String path, byte[] data) throws Exception {
        Files.write(Paths.get(path), data);
    }

    public static void writeText(String path, String content) throws Exception {
        Files.writeString(Paths.get(path), content);
    }

    public static String readText(String path) throws Exception {
        return Files.readString(Paths.get(path));
    }
}
