package Model;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.*;

import javax.crypto.Cipher;

public class RSAUtil {
    private static final String ALGORITHM = "RSA";

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    public static byte[] encrypt(byte[] data, PublicKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] encryptedData, PrivateKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encryptedData);
    }

    public static void saveKey(byte[] keyBytes, String filePath) throws Exception {
        Files.write(Paths.get(filePath), keyBytes);
    }

    public static PrivateKey loadPrivateKey(String filePath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(filePath));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
        return factory.generatePrivate(spec);
    }

    public static PublicKey loadPublicKey(String filePath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(filePath));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
        return factory.generatePublic(spec);
    }
}
