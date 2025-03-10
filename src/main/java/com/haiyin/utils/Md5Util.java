package com.haiyin.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    public static void main(String[] args) {
        String input = "Hello, World!"; // 您可以替换为任何字符串
        String md5Hash = getMd5String(input);
        System.out.println("MD5 Hash: " + md5Hash);
    }

    public static String getMd5String(String s) {
        return getMd5String(s.getBytes());
    }

    public static String getMd5String(byte[] bytes) {
        try {
            // 创建 MD5 MessageDigest 实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算输入字节数组的哈希值
            byte[] messageDigest = md.digest(bytes);
            // 将字节数组转为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

