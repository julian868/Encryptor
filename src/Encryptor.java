import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Encryptor {
    public String encrypt(String algorithm, String input, SecretKey key, IvParameterSpec iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        byte[] iVec = iv.getIV();
        byte[] vecCypher = Arrays.copyOf(iVec, cipherText.length + iVec.length);
        System.arraycopy(cipherText, 0, vecCypher, iVec.length, cipherText.length);     //merge iv vector bytes with cipherText bytes
        return Base64.getEncoder().encodeToString(vecCypher);
    }

    public String decrypt(String algorithm, String cipherText, SecretKey key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        
        byte[] vecCipher = Base64.getDecoder().decode(cipherText);      //decode text to bytes
        byte[] vecBytes = Arrays.copyOf(vecCipher, 16);     //new array to store bytes of iv
        IvParameterSpec iv = new IvParameterSpec(vecBytes);
        byte[] cipherBytes = new byte[vecCipher.length - 16];         //new array to store bytes of cipherText 16 is size of iv byte array
        cipherBytes = Arrays.copyOfRange(vecCipher, 16, vecCipher.length);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(cipherBytes);
        return new String(plainText);
    }
}
