package Model;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Base64;

public class DESUtil {
    private static final String ALGORITHM = "DES/CBC/PKCS5Padding";
    private static final int KEY_SIZE = 56;

    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(KEY_SIZE);
        return keyGen.generateKey();
    }

    public static byte[] encrypt(byte[] data, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] encrypted, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        return cipher.doFinal(encrypted);
    }

    public static void encryptToFile(String inputPath, String outputPath, SecretKey key) throws Exception {
        byte[] data = Files.readAllBytes(Paths.get(inputPath));
        byte[] iv = new byte[8];
        new SecureRandom().nextBytes(iv);

        byte[] encrypted = encrypt(data, key, iv);

        String result = "DES_ENCRYPTED\n" +
                Base64.getEncoder().encodeToString(iv) + "\n" +
                Base64.getEncoder().encodeToString(key.getEncoded()) + "\n" +
                Base64.getEncoder().encodeToString(encrypted);

        Files.write(Paths.get(outputPath), result.getBytes());
    }

    public static void decryptToFile(String inputPath, String outputPath, SecretKey key) throws Exception {
        String[] lines = new String(Files.readAllBytes(Paths.get(inputPath))).split("\\n", 4);
        if (!lines[0].equals("DES_ENCRYPTED")) {
            throw new IllegalArgumentException("Invalid DES file format");
        }
        byte[] iv = Base64.getDecoder().decode(lines[1]);
        byte[] keyBytes = Base64.getDecoder().decode(lines[2]);
        byte[] encrypted = Base64.getDecoder().decode(lines[3]);

        SecretKey restoredKey = new SecretKeySpec(keyBytes, "DES");
        byte[] decrypted = decrypt(encrypted, restoredKey, iv);

        Files.write(Paths.get(outputPath), decrypted);
    }
}