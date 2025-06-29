package View;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleView {
    private final Scanner scanner = new Scanner(System.in);

    public int chooseInputType() {
        System.out.println("\n=== File Encryption/Decryption Tool ===");
        System.out.println("Input type: [1] Text  [2] File [0] Exit");
        System.out.print("Enter your choice: ");
        int choice = readInt();
        return choice;
    }

    public String getTextInput() {
        System.out.print("Enter text: ");
        return scanner.nextLine();
    }

    public String getFilePath() {
        System.out.print("Enter path to file: ");
        return scanner.nextLine();
    }

    public int chooseAlgorithm() {
        System.out.println("Choose algorithm:");
        System.out.println("[1] AES\n[2] DES\n[3] RSA\n[4] Base64\n[5] Hash (MD5, SHA-1, SHA-256)\n[0] Exit ");
        System.out.print("Enter your choice: ");
        return readInt();
    }

    public int chooseOperation() {
        System.out.println("Choose operation: [1] Encrypt/Encode/Hash  [2] Decrypt/Decode: ");
        System.out.print("Enter your choice: ");
        return readInt();
    }

    public int chooseHashType() {
        System.out.println("Choose hash type:");
        System.out.println("[1] MD5\n[2] SHA-1\n[3] SHA-256");
        System.out.print("Enter your choice: ");
        return readInt();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayResult(String label, String result) {
        System.out.println(label + ": " + result);
    }

    public void close() {
        scanner.close();
    }

    private int readInt() {
        while (true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                return value;
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.nextLine();
            }
        }
    }
}
