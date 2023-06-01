import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.KeyStore.SecretKeyEntry;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class KeyGen {
    public SecretKey genKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(n);
        SecretKey key = keyGen.generateKey();
        return key;
    }

    public SecretKey genPassKey(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 67890, 128);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        return secret;
    }

    public void storeKey(SecretKey key)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        //load KeyStore object
        char[] password = "changeit".toCharArray();
        String path = "C:/Program Files/Java/jdk-18.0.1.1/lib/security/cacerts";
        java.io.FileInputStream fis = new FileInputStream(path);
        keyStore.load(fis, password);

        //create KeyStore.ProtectionParameter object
        KeyStore.ProtectionParameter protectionParam = new KeyStore.PasswordProtection(password);

        //create SecretKeyEntry object
        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(key);
        keyStore.setEntry("secretKeyAlias", secretKeyEntry, protectionParam);

        //store KeyStore object
        java.io.FileOutputStream fos = new java.io.FileOutputStream("AESKey");
        keyStore.store(fos, password);
        System.out.println("Encryption Key Stored");

    }

    public SecretKey loadKey() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableEntryException {
        //create KeyStore object
        KeyStore keyStore = KeyStore.getInstance("JCEKS");

        //load KeyStore object
        char[] password = "changeit".toCharArray();
        String path = "C:/Program Files/Java/jdk-18.0.1.1/lib/security/cacerts";
        java.io.FileInputStream fis = new FileInputStream(path);
        keyStore.load(fis, password);

        //create KeyStore.ProtectionParameter object
        KeyStore.ProtectionParameter protectionParam = new KeyStore.PasswordProtection(password);

        //create KeyStore.SecretKeyEntry object
        SecretKeyEntry secretKeyEntry = (SecretKeyEntry) keyStore.getEntry("secretKeyAlias", protectionParam);

        SecretKey secretKey = secretKeyEntry.getSecretKey();
        System.out.println("Algorithm used to generate key: " + secretKey.getAlgorithm());
        System.out.println("Format used for the Key: " + secretKey.getFormat());
        return secretKey;
    }
}
