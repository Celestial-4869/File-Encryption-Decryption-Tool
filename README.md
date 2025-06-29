# File Encryption/Decryption Tool

This is a Java-based console application that provides functionality for encrypting, decrypting, encoding, and hashing both text and files using common cryptographic algorithms. The application follows the MVC (Model-View-Controller) architectural pattern.

## Features

- Encrypt and decrypt files using:
    - AES (Advanced Encryption Standard)
    - DES (Data Encryption Standard)
    - RSA (asymmetric encryption, for text only)
- Encode and decode using:
    - Base64
- Hashing support for:
    - MD5
    - SHA-1
    - SHA-256
- Supports both plain text input and file input
- Output is written to a new file to avoid overwriting the original
- RSA key pairs are generated automatically and saved to file when used

## Technologies Used

- Java 24+
- Standard Java libraries (no external dependencies)

## Project Structure

