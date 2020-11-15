package com.pkgs.bullshit.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * md5转换器
 *
 * @author huapeng.huang
 * @version V1.0
 * @since 2020-11-14 15:31
 */
public class Md5Util {

    private static MessageDigest digest = null;

    /**
     * 将数据转成md5字符串
     *
     * @param data 数据
     * @return String
     */
    public synchronized static String encode(String data) {
        if (Objects.isNull(data)) {
            return null;
        }
        if (Objects.isNull(digest)) {
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException nsae) {
                nsae.printStackTrace();
            }
        }
        digest.update(data.getBytes());
        return toHex(digest.digest());
    }

    /**
     * 转换成16进制数据
     *
     * @param hash arr
     * @return String
     */
    private static String toHex(byte[] hash) {
        StringBuilder buf = new StringBuilder(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if (((int) hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        System.out.println(encode("2nd & 11"));
    }
}
