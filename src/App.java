import java.util.Scanner;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class App {
    public static void main(String[] args) throws Exception {
        String algorithm = "AES/CBC/PKCS5Padding";
        IvParameterSpec ivParameterSpec = new IvGen().ivGen();
        SecretKey key = new KeyGen().loadKey();
        Scanner scan = new Scanner(System.in);
        int c = 0;
        while (c != 3) {
            System.out.println("Encrypt(1) Decrypt(2) Close(3)");
            c = Integer.parseInt(scan.nextLine());
            switch (c) {
                case 1: {
                    System.out.println("Enter Text To Encrypt:");
                    String inputText = scan.nextLine();
                    String cipherText = new Encryptor().encrypt(algorithm, inputText, key, ivParameterSpec);
                    // String plainText = new Encryptor().decrypt(algorithm, cipherText, key, ivParameterSpec);
                    // if (inputText.equals(plainText))
                    //     System.out.println("Encryption Successful");
                    System.out.println("Cipher Text:\n" + cipherText);
                    //System.out.println("Original Text: " + new Encryptor().decrypt(algorithm, cipherText, key, ivParameterSpec));
                    break;
                }
                case 2: {
                    System.out.println("Enter CypherText To Decrypt");
                    String inputCypher = scan.nextLine();
                    String plainText = new Encryptor().decrypt(algorithm, inputCypher, key);
                    System.out.println("Decrypted Text:\n" + plainText);
                    break;
                }
                case 3:
                    break;
                default: {
                    System.out.println("Invalid Entry");
                    break;
                }
            }
        }
        scan.close();
    }
}
