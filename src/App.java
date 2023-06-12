import java.util.Scanner;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class App {
    public static void main(String[] args) throws Exception {
        String algorithm = "AES/CBC/PKCS5Padding";
        Scanner scan = new Scanner(System.in);
        int c = 0;
        while (c != 3) {
            System.out.println("Encrypt(1) Decrypt(2) Close(3)");
            c = Integer.parseInt(scan.nextLine());
            switch (c) {
                case 1: {
                    System.out.println("Enter Text To Encrypt:");
                    String inputText = scan.nextLine();
                    System.out.println("Enter Secret Key:");
                    String password = scan.nextLine();
                    IvParameterSpec ivParameterSpec = new IvGen().ivGen();
                    SecretKey key = new KeyGen().genPassKey(password, algorithm);
                    String cipherText = new Encryptor().encrypt(algorithm, inputText, key, ivParameterSpec);
                    System.out.println("Cipher Text:\n" + cipherText);
                    break;
                }
                case 2: {
                    System.out.println("Enter CypherText To Decrypt");
                    String inputCypher = scan.nextLine();
                    System.out.println("Enter Secret Key:");
                    String password = scan.nextLine();
                    SecretKey key = new KeyGen().genPassKey(password, algorithm);
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
