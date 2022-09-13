import java.security.NoSuchAlgorithmException;

public class MessageDigest {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256"); // secure Hash Algorithm.
        String msg = "Hell Crypto";
        digest.update(msg.getBytes()); // data를 digest에 전달

        byte[] hash = digest.digest(); // 해쉬값을 생성
    }
    public static String byteTohex(byte[] btyes) {
        StringBuilder bulider = new StringBuilder();
        for(byte b : bytes) bulider.append(String.format("%02x", b));
        return builder;
    }
}

