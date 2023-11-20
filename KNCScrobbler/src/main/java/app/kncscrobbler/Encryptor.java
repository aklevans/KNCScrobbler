package app.kncscrobbler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {
    
    /**
     * From https://www.baeldung.com/java-aes-encryption-decryption
     * @param algorithm
     * @param input
     * @param key
     * @param iv
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private static String encrypt(String algorithm, String input, SecretKey key, IvParameterSpec iv) 
            throws NoSuchPaddingException, NoSuchAlgorithmException,
        InvalidAlgorithmParameterException, InvalidKeyException,
        BadPaddingException, IllegalBlockSizeException {
        
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
            .encodeToString(cipherText);
    }
    
    /**
     * https://www.baeldung.com/java-aes-encryption-decryption
     * @param algorithm
     * @param cipherText
     * @param key
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private static String decrypt(String algorithm, String cipherText, SecretKey key, IvParameterSpec iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
            
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
            return new String(plainText);
        }
    
    
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
    
    public static String generateIvString() {
        return getIvString(generateIv());
    }
    
    public static String getIvString(IvParameterSpec i) {
        String ivString = Base64.getEncoder().encodeToString(i.getIV());
        return ivString;
    }
    
    public static IvParameterSpec getIv(String ivString) {
        byte[] iv = Base64.getDecoder().decode(ivString);
        return new IvParameterSpec(iv);
    }
    
    public static String encrypt(String input, String ivString) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        
        String aesKey = "";
        try (InputStream i = new FileInputStream("src/main/resources/static/config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(i);
            
            // get the property value and print it out
            aesKey = prop.getProperty( "aes_key");


        } catch (IOException ex) {
            ex.printStackTrace();
            aesKey = System.getenv("AES_KEY");
            
            
        }
        
        // decode the base64 encoded string
        byte[] decodedKey = Base64.getDecoder().decode(aesKey);
        IvParameterSpec ivParameterSpec = getIv(ivString);
        
        // rebuild key using SecretKeySpec
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); 
        
        String cipherText = encrypt("AES/CBC/PKCS5Padding", input, originalKey, ivParameterSpec);
        return cipherText;

    }
    
    public static String decrypt(String cipherText, String ivString) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        String aesKey = "";
        try (InputStream i = new FileInputStream("src/main/resources/static/config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(i);
            
            // get the property value and print it out
            aesKey = prop.getProperty( "aes_key");


        } catch (IOException ex) {
            ex.printStackTrace();
            aesKey = System.getenv("AES_KEY");
            
            
        }
        
     // decode the base64 encoded string
        byte[] decodedKey = Base64.getDecoder().decode(aesKey);
        
        IvParameterSpec ivParameterSpec = getIv(ivString);
        // rebuild key using SecretKeySpec
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); 
        
        String plaintext = decrypt("AES/CBC/PKCS5Padding", cipherText, originalKey, ivParameterSpec);
        return plaintext;
    }
}
