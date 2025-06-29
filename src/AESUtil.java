import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.MessageDigest;

public class AESUtil {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    public static SecretKey getKeyFromPassword(String password) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(password.getBytes("UTF-8"));
        return new SecretKeySpec(key, "AES");
    }

    public static byte[] encrypt(byte[] data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(new byte[16]);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] encrypted, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(new byte[16]);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(encrypted);
    }
}
