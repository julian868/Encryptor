import java.util.Scanner;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class App {
    public static void main(String[] args) throws Exception {
        SecretKey key = new KeyGen().genKey(128);
        String algorithm = "AES/CBC/PKCS5Padding";
        IvParameterSpec ivParameterSpec = new IvGen().ivGen();

        System.out.println("Encrypt(1) Decrypt(2)");
        Scanner scan = new Scanner(System.in);
        int c = Integer.parseInt(scan.nextLine());
        switch(c) {
            case 1: {
                System.out.println("Enter Text To Encrypt:");
                String inputText = scan.nextLine();
                String cipherText = new Encryptor().encrypt(algorithm, inputText, key, ivParameterSpec);
                String plainText = new Encryptor().decrypt(algorithm, cipherText, key, ivParameterSpec);
                if (inputText.equals(plainText))
                    System.out.println("Encryption Successful");
                System.out.println("Cipher Text:\n" + cipherText);
                break;
            }
            case 2: {
                System.out.println("Enter CypherText To Decrypt");
                String inputCypher = scan.nextLine();
                String plainText = new Encryptor().decrypt(algorithm, inputCypher, key, ivParameterSpec);
                System.out.println("Decrypted Text: " + plainText);
            }
            default: {
                System.out.println("Invalid Entry");
            }
        }        
        scan.close();        
    }
}
