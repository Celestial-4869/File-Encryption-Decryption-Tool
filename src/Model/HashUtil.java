package Model;

import java.security.MessageDigest;
import java.util.Base64;

public class HashUtil {

    public static String hash(byte[] data, String algorithm) throws Exception {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] hashed = md.digest(data);
        return Base64.getEncoder().encodeToString(hashed);
    }

    public static String md5(String input) throws Exception {
        return hash(input.getBytes(), "MD5");
    }

    public static String sha1(String input) throws Exception {
        return hash(input.getBytes(), "SHA-1");
    }

    public static String sha256(String input) throws Exception {
        return hash(input.getBytes(), "SHA-256");
    }

    public static String md5(byte[] input) throws Exception {
        return hash(input, "MD5");
    }

    public static String sha1(byte[] input) throws Exception {
        return hash(input, "SHA-1");
    }

    public static String sha256(byte[] input) throws Exception {
        return hash(input, "SHA-256");
    }
}
