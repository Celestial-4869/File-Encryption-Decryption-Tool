package Controller;

import Model.*;
import View.ConsoleView;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Base64;

public class MainController {
    private final ConsoleView view = new ConsoleView();

    public void run() {
        try {
            while (true) {
                int algoChoice = view.chooseAlgorithm();
                if (algoChoice == 0) break;

                int inputType;
                switch (algoChoice) {
                    case 1, 2, 4, 5 -> inputType = view.chooseInputType();
                    case 3 -> inputType = 1; // RSA chỉ hỗ trợ văn bản
                    default -> {
                        view.displayMessage("Invalid algorithm.");
                        continue;
                    }
                }

                String input = (inputType == 1) ? view.getTextInput() : view.getFilePath();
                int operation = view.chooseOperation();

                switch (algoChoice) {
                    case 1: // AES
                        if (inputType == 2) {
                            if (operation == 1) {
                                String outputPath = input.replaceFirst("\\.[^.]+$", "");
                                AESUtil.encryptToFile(input, outputPath);
                                view.displayMessage("AES Encrypted to: " + outputPath);
                            } else {
                                String outputPath = input + ".dec";
                                AESUtil.decryptToFile(input, outputPath);
                                view.displayMessage("AES Decrypted to: " + outputPath);
                            }
                        } else {
                            view.displayMessage("AES currently supports file mode only.");
                        }
                        break;

                    case 2: // DES
                        if (inputType == 2) {
                            if (operation == 1) {
                                String outputPath = input.replaceFirst("\\.[^.]+$", "");
                                DESUtil.encryptToFile(input, outputPath, KeyService.generateKey(EncryptionType.DES));
                                view.displayMessage("DES Encrypted to: " + outputPath);
                            } else {
                                String outputPath = input + ".dec";
                                DESUtil.decryptToFile(input, outputPath, KeyService.generateKey(EncryptionType.DES));
                                view.displayMessage("DES Decrypted to: " + outputPath);
                            }
                        } else {
                            view.displayMessage("DES currently supports file mode only.");
                        }
                        break;

                    case 3: // RSA
                        if (operation == 1) {
                            KeyPair keys = RSAUtil.generateKeyPair();
                            byte[] encrypted = RSAUtil.encrypt(input.getBytes(), keys.getPublic());
                            Files.write(Paths.get("rsa_pub.key"), keys.getPublic().getEncoded());
                            Files.write(Paths.get("rsa_priv.key"), keys.getPrivate().getEncoded());
                            view.displayResult("Encrypted (Base64)", Base64.getEncoder().encodeToString(encrypted));
                        } else {
                            byte[] encryptedData = Base64.getDecoder().decode(input);
                            PrivateKey privKey = RSAUtil.loadPrivateKey("rsa_priv.key");
                            byte[] decrypted = RSAUtil.decrypt(encryptedData, privKey);
                            view.displayResult("Decrypted", new String(decrypted));
                        }
                        break;

                    case 4: // Base64
                        if (inputType == 1) {
                            if (operation == 1) {
                                view.displayResult("Encoded", Base64Service.encode(input.getBytes()));
                            } else {
                                byte[] decoded = Base64Service.decode(input);
                                view.displayResult("Decoded", new String(decoded));
                            }
                        } else {
                            if (operation == 1) {
                                String outputPath = input.replaceFirst("\\.[^.]+$", "");
                                Base64Service.encodeToFile(input, outputPath);
                                view.displayMessage("File encoded in Base64 to: " + outputPath);
                            } else {
                                String outputPath = input + ".dec";
                                Base64Service.decodeToFile(input, outputPath);
                                view.displayMessage("File decoded from Base64 to: " + outputPath);
                            }
                        }
                        break;

                    case 5: // Hash
                        int hashType = view.chooseHashType();
                        String hashResult;
                        if (inputType == 1) {
                            hashResult = switch (hashType) {
                                case 1 -> HashUtil.md5(input);
                                case 2 -> HashUtil.sha1(input);
                                case 3 -> HashUtil.sha256(input);
                                default -> "Invalid hash option";
                            };
                        } else {
                            byte[] fileBytes = FileUtil.readBytes(input);
                            hashResult = switch (hashType) {
                                case 1 -> HashUtil.md5(fileBytes);
                                case 2 -> HashUtil.sha1(fileBytes);
                                case 3 -> HashUtil.sha256(fileBytes);
                                default -> "Invalid hash option";
                            };
                        }
                        view.displayResult("Hash", hashResult);
                        break;

                    case 0:
                        if (algoChoice == 0) break;
                    default:
                        view.displayMessage("Invalid algorithm selection.");
                }
            }
        } catch (Exception e) {
            view.displayMessage("Error: " + e.getMessage());
        } finally {
            view.displayMessage("Goodbye!");
            view.close();
        }
    }
}
