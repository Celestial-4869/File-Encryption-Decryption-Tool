package Model;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class KeyService {

    public static SecretKey generateKey(EncryptionType type) throws Exception {
        String algorithm = switch (type) {
            case AES -> "AES";
            case DES -> "DES";
            default -> throw new IllegalArgumentException("Unsupported encryption type");
        };

        KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
        if (type == EncryptionType.AES) {
            keyGen.init(128);
        } else {
            keyGen.init(56);
        }
        return keyGen.generateKey();
    }
}