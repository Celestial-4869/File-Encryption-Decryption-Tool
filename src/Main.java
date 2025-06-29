import java.util.Scanner;
import javax.crypto.SecretKey;
import java.security.KeyPair;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("==== Java Crypto Toolkit ====");
            System.out.println("1. Encrypt/Decrypt (AES, DES)");
            System.out.println("2. Encrypt/Decrypt (RSA)");
            System.out.println("3. Encode/Decode (Base64)");
            System.out.println("4. Hash (MD5, SHA1, SHA256, SHA512)");
            System.out.print("Select option (1-4): ");
            int option = Integer.parseInt(sc.nextLine());

            // ---- Chọn kiểu dữ liệu đầu vào ----
            System.out.println("Choose input type:");
            System.out.println("1. File");
            System.out.println("2. Text input");
            System.out.print("Your choice: ");
            int inputType = Integer.parseInt(sc.nextLine());

            byte[] inputData;
            if (inputType == 1) {
                System.out.print("Enter file path: ");
                String path = sc.nextLine();
                inputData = FileUtils.readFile(path);
            } else {
                System.out.print("Enter your text: ");
                String text = sc.nextLine();
                inputData = text.getBytes();
            }

            switch (option) {
                case 1 -> {
                    System.out.print("Choose: 1.AES  2.DES: ");
                    int t = Integer.parseInt(sc.nextLine());
                    EncryptionType type = (t == 1) ? EncryptionType.AES : EncryptionType.DES;

                    System.out.print("Encrypt or Decrypt? (e/d): ");
                    String choice = sc.nextLine();

                    System.out.print("Enter password: ");
                    String password = sc.nextLine();

                    SecretKey key = (type == EncryptionType.AES)
                            ? AESUtil.getKeyFromPassword(password)
                            : DESUtil.getKeyFromPassword(password);

                    byte[] output = (choice.equals("e"))
                            ? (type == EncryptionType.AES ? AESUtil.encrypt(inputData, key) : DESUtil.encrypt(inputData, key))
                            : (type == EncryptionType.AES ? AESUtil.decrypt(inputData, key) : DESUtil.decrypt(inputData, key));

                    FileUtils.writeFile("out_" + choice + "_" + type + ".bin", output);
                    System.out.println("Done: out_" + choice + "_" + type + ".bin");
                }
                case 2 -> {
                    System.out.print("Encrypt or Decrypt? (e/d): ");
                    String choice = sc.nextLine();

                    KeyPair kp = RSAUtil.generateKeyPair();
                    byte[] output = (choice.equals("e"))
                            ? RSAUtil.encrypt(inputData, kp.getPublic())
                            : RSAUtil.decrypt(inputData, kp.getPrivate());

                    FileUtils.writeFile("out_rsa_" + choice + ".bin", output);
                    System.out.println("Done: out_rsa_" + choice + ".bin");
                }
                case 3 -> {
                    System.out.print("Encode or Decode? (e/d): ");
                    String choice = sc.nextLine();

                    if (choice.equals("e")) {
                        String encoded = Base64Util.encode(inputData);
                        System.out.println("Encoded: " + encoded);
                    } else {
                        byte[] decoded = Base64Util.decode(new String(inputData));
                        System.out.println("Decoded: " + new String(decoded));
                    }
                }
                case 4 -> {
                    System.out.println("Choose hash: MD5, SHA-1, SHA-256, SHA-512");
                    String algo = sc.nextLine().toUpperCase();
                    String digest = HashUtil.hash(inputData, algo);
                    System.out.println(algo + " hash: " + digest);
                }
                default -> System.out.println("Invalid option.");
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}
