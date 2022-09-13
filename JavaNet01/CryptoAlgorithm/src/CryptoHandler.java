import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class CryptoHandler {
    private static Cipher ecipher; // 암호화
    private static Cipher dcipher; // 복호화
    private static SecretKey key; // 암호화 키

    public static void main(String[] args) throws
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        // 암호화 알고리즘의 키 생성 (DES,AES)
        key = KeyGenerator.getInstance("DES").generateKey();
        ecipher = Cipher.getInstance("DES");
        dcipher = Cipher.getInstance("DES");
        // RSA 알고리즘은 public key와 private key 두개가 필요함.
        // RSA로 만드는 방법
        /*
        ecipher = Cipher.getInstance("RSA");
        dcipher = Cipher.getInstance("RSA");
        KeyPairGenerator pairGenerator = KeyPairGenerator.getInstance("RSA");
        KeyPair pair = pairGenerator.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) pair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) pair.getPrivate();
        */
        // 생성한 암호키를 가지고 암호화 / 복호화 초기화
        ecipher.init(Cipher.ENCRYPT_MODE, key);
        dcipher.init(Cipher.DECRYPT_MODE, key); // 암호화, 복호화에 동일한 키 사용, 대칭키
        String cipherText = encrypt("Hello Java Security Network Programming!!");
        System.out.println("Encrypted: " + cipherText);

        String plainText = decrypt(cipherText);
        System.out.println("Decrypted: " + plainText);
    }
    private static String encrypt(String ptext) throws
            UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        byte[] utf8 = ptext.getBytes("UTF8");
        byte[] ctext = ecipher.doFinal(utf8); // 암호화 수행
        ctext = Base64.getEncoder().encode(ctext);
        return new String(ctext);
    }
    private static String decrypt(String ctext) throws IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        byte[] dec = Base64.getDecoder().decode(ctext);
        byte[] ptext = dcipher.doFinal(dec);
        return new String(ptext, "UTF8");
    }
}
