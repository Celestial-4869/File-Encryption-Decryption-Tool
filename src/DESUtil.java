import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.MessageDigest;

public class DESUtil {
    private static final String ALGORITHM = "DES/CBC/PKCS5Padding";

    public static SecretKey getKeyFromPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] key = md.digest(password.getBytes("UTF-8"));
        byte[] desKey = new byte[8];
        System.arraycopy(key, 0, desKey, 0, 8);
        return new SecretKeySpec(desKey, "DES");
    }

    public static byte[] encrypt(byte[] data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(data);
    }
}
