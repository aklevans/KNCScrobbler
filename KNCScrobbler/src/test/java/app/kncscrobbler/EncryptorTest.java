package app.kncscrobbler;

import static org.junit.jupiter.api.Assertions.*;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;

class EncryptorTest {


    
    @Test
    void test () throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        
        
        
        String s = "test";
        String iv = Encryptor.getIvString( Encryptor.generateIv() );
        String ciphertext = Encryptor.encrypt( s, iv);
        String decoded = Encryptor.decrypt( ciphertext , iv);
        assertEquals(decoded, s);
    }

}
