package com.pkgs.bullshit.util;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author huapeng.huang
 * @version V1.0
 * @since 2020-11-14 15:50
 */
public class AesUtil {

    private static final String KEY_ALGORITHM = "AES";

    private static final String KEY_RANDOM="SHA1PRNG";

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @param key     加密密钥
     * @return 返回Base64转码后的加密数据
     */
    public static String encode(String content, String key) {
        SecretKey secretKey = getSecretKey(key);
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] p = content.getBytes(StandardCharsets.UTF_8);
            byte[] result = cipher.doFinal(p);
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * AES 解密操作
     *
     * @param content content
     * @param key     key
     * @return String
     */
    public static String decode(String content, String key) {
        SecretKey secretKey = getSecretKey(key);
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] c = decoder.decodeBuffer(content);
            byte[] result = cipher.doFinal(c);

            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成加密秘钥
     *
     * @return SecretKey
     */
    private static SecretKey getSecretKey(final String key) {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance(KEY_RANDOM);
            secureRandom.setSeed(key.getBytes());
            KeyGenerator generator = KeyGenerator.getInstance(KEY_ALGORITHM);
            generator.init(secureRandom);
            return generator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String key = "cs12110";
        String content = "橄榄球: 2nd & 11";

        String encode = AesUtil.encode(content, key);
        String decode = AesUtil.decode(encode, key);

        System.out.println("待加密字符串:" + content);
        System.out.println("加密后字符串:" + encode);
        System.out.println("解密后字符串:" + decode);
    }
}
