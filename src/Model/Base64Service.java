package Model;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class Base64Service {

    public static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] decode(String encoded) {
        return Base64.getDecoder().decode(encoded);
    }

    public static void encodeToFile(String inputPath, String outputPath) throws Exception {
        byte[] inputData = Files.readAllBytes(Paths.get(inputPath));
        String encoded = encode(inputData);
        Files.writeString(Paths.get(outputPath), "BASE64_ENCODED\n" + encoded);
    }

    public static void decodeToFile(String inputPath, String outputPath) throws Exception {
        String content = Files.readString(Paths.get(inputPath));
        if (!content.startsWith("BASE64_ENCODED\n")) {
            throw new IllegalArgumentException("Invalid Base64 encoded file format");
        }
        String encoded = content.substring("BASE64_ENCODED\n".length());
        byte[] decoded = decode(encoded);
        Files.write(Paths.get(outputPath), decoded);
    }
}
