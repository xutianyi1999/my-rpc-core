package club.koumakan.rpc.commons;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

public class EncryptContext {

    public final static long RANDOM_VALUE = 324435435;

    // 解密map
    public final static Map<String, Cipher> decryptMap = new ConcurrentHashMap<>();

    // 加密map
    public final static Map<String, Cipher> encryptMap = new ConcurrentHashMap<>();

    public static void addCipher(String key, InetSocketAddress inetSocketAddress) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        decryptMap.put(translateMapKey(inetSocketAddress), getCipher(key, DECRYPT_MODE));
        encryptMap.put(translateMapKey(inetSocketAddress), getCipher(key, ENCRYPT_MODE));
    }

    public static void removeCipher(InetSocketAddress inetSocketAddress) {
        decryptMap.remove(translateMapKey(inetSocketAddress));
        encryptMap.remove(translateMapKey(inetSocketAddress));
    }

    public static String translateMapKey(InetSocketAddress inetSocketAddress) {
        InetAddress inetAddress = inetSocketAddress.getAddress();
        String ipAddress;

        if (inetAddress.isAnyLocalAddress() || inetAddress.isLoopbackAddress()) {
            ipAddress = "0.0.0.0";
        } else {
            ipAddress = inetAddress.getHostAddress();
        }
        System.out.println(ipAddress);
        return ipAddress + ":" + inetSocketAddress.getPort();
    }

    private static Cipher getCipher(String key, int mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key.getBytes(StandardCharsets.UTF_8));

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(mode, secretKey);
        return cipher;
    }
}