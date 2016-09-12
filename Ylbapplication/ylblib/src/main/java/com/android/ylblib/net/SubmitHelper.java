package com.android.ylblib.net;

import android.content.Context;


import com.android.ylblib.base.BaseApplication;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 提交问题帮助类
 *
 * @author fanxi
 * @date May 30, 2013
 * <p/>
 * NDK编译命令 ~/android/android-ndk-r8e/ndk-build
 */

public class SubmitHelper {
    static {
        System.loadLibrary("stlport_shared");
        System.loadLibrary("botan");
        System.loadLibrary("spam");
    }

    // 定义加密算法，有DES、DESede(即3DES)、Blowfish
    private static final String Algorithm = "DESede/ECB/PKCS5Padding";

    /**
     * 加密方法
     *
     * @param src 源数据的字节数组
     * @return
     */
    public static byte[] encryptMode(byte[] src) {
        try {
            DESedeKeySpec dks = new DESedeKeySpec(build3DesKey());
            SecretKeyFactory keyFactory = SecretKeyFactory
                    .getInstance("DESede");
            SecretKey deskey = keyFactory.generateSecret(dks);
            Cipher c1 = Cipher.getInstance(Algorithm); // 实例化负责加密/解密的Cipher工具类
            c1.init(Cipher.ENCRYPT_MODE, deskey); // 初始化为加密模式
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 解密函数
     *
     * @param src 密文的字节数组
     * @return
     */
    public static byte[] decryptMode(byte[] src) {
        try {
            DESedeKeySpec dks = new DESedeKeySpec(build3DesKey());
            SecretKeyFactory keyFactory = SecretKeyFactory
                    .getInstance("DESede");
            SecretKey deskey = keyFactory.generateSecret(dks);
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey); // 初始化为解密模式
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /*
     * 根据字符串生成密钥字节数组
     *
     * @param keyStr 密钥字符串
     *
     * @return
     *
     * @throws UnsupportedEncodingException
     */
    public static byte[] build3DesKey() throws UnsupportedEncodingException {
        String keyStr = init(BaseApplication.getContext());
        byte[] key = new byte[24]; // 声明一个24位的字节数组，默认里面都是0
        byte[] temp = keyStr.getBytes("UTF-8"); // 将字符串转成字节数组

		/*
         * 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		 */
        if (key.length > temp.length) {
            // 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            // 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }

    /**
     * 初始化
     *
     * @param context
     * @return
     */
    public static native String init(Context context);

    /**
     * 初始化
     *
     * @param context
     * @return
     */
    private static final String HMAC_ENCRYP = "HmacSHA256";
    public static final String HTTP_REQUEST_TYPE = "POST";

    /**
     * hmac sha256加密
     */
    public static String encryptionCall(String data, String token) {
        String checksum = "";
        try {
            Mac mac = Mac.getInstance(HMAC_ENCRYP);
            byte[] secretByte = token.getBytes("UTF-8");
            byte[] dataBytes = data.getBytes("UTF-8");
            SecretKey secret = new SecretKeySpec(secretByte, HMAC_ENCRYP);

            mac.init(secret);
            byte[] doFinal = mac.doFinal(dataBytes);
            byte[] hexB = new Hex().encode(doFinal);
            checksum = new String(hexB);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return checksum;
    }

    public static String getHmacSignature(Map<String, Object> treeMap,
                                          String token, String api) {
        String hmac = "";
        boolean isFirst = true;
        // String lineSeparator = System.getProperty("line.separator");
        String lineSeparator = "&";
        Set<String> keySet = treeMap.keySet();
        Iterator<String> iter = keySet.iterator();
        StringBuilder sb = new StringBuilder(HTTP_REQUEST_TYPE + lineSeparator
                + api + lineSeparator);
        while (iter.hasNext()) {
            String key = iter.next();
            if (isFirst) {
                sb.append(key + "=" + treeMap.get(key));
                isFirst = false;
            } else {
                sb.append("&" + key + "=" + treeMap.get(key));
            }
        }
        hmac = SubmitHelper.encryptionCall(sb.toString(), token);
        return hmac;
    }

}
