package com.zyz.work;

import com.zyz.Interview.MyArrayList;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class wk {
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static void main(String[] args) {
        liyuewangguan();
    }

    private static void liyuewangguan() {
        //
        System.out.println("*********gsdf***********");
//        String s = md5("*U2dKuPljTgJG2").toLowerCase().trim();
        String s = md5("19302827407").toLowerCase().trim();
        System.out.println(s);

//        System.out.println("********************");
//        bcryptTest();

        System.out.println("********************");
//        apply("K2pRkyqRVaMNTy8O4bblaXpaoTeGnVTM");
        apply("NReiQWR8POV0N0GGtQQ2pZtu7YpX6qsQ");

        System.out.println("********************");
        System.out.println(trimStringWith("absddaaa", 'a'));

        System.out.println("********************");
        jwt jwtUtil = new jwt();
        jwtUtil.jwtUtilMain();

        System.out.println("********************");
        MyArrayList myArrayList = new MyArrayList();
        myArrayList.add(1);
        myArrayList.add(2);
        myArrayList.add(3);
        System.out.println(myArrayList.indexOf(4));
        System.out.println(myArrayList.contain(2));
        for (int i = 0; i < myArrayList.getSize(); i++) {
            System.out.println(myArrayList.get(i));
        }
    }

    public static String trimStringWith(String str, char beTrim) {
        int st = 0;
        int len = str.length();
        char[] val = str.toCharArray();
        char sbeTrim = beTrim;
        while ((st < len) && (val[st] <= sbeTrim)) {
            st++;
        }
        while ((st < len) && (val[len - 1] <= sbeTrim)) {
            len--;
        }
        return ((st > 0) || (len < str.length())) ? str.substring(st, len) : str;
    }

    //    public void apply(RequestTemplate template, String paastoken) {
    public static void apply(String paastoken) {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonce = UUID.randomUUID().toString();
        String sign = sign(timestamp, paastoken, nonce);
        System.out.println("sign: " + sign);
        System.out.println("timestamp: " + timestamp);
        System.out.println("nonce: " + nonce);
    }

    private static String sign(String timestamp, String paastoken, String nonce) {
        return SHA256(timestamp + paastoken + nonce + timestamp, "SHA-256");
    }

    private static String SHA256(final String text, final String type) {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (text != null && text.length() > 0) {
            try {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance(type);
                // 传入要加密的字符串
                messageDigest.update(text.getBytes());
                // 得到 byte 類型结果
                byte byteBuffer[] = messageDigest.digest();

                // 將 byte 轉換爲 string
                StringBuffer strHexString = new StringBuffer();
                // 遍歷 byte buffer
                for (int i = 0; i < byteBuffer.length; i++) {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return strResult;
    }

    public static void bcryptTest() {
        // 用户密码
        String password = "lzhonghuo";
        // 创建密码加密的对象
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 密码加密
        String newPassword = passwordEncoder.encode(password);
        System.out.println("加密后的密码为：" + newPassword);

        // 校验这两个密码是否是同一个密码
        // matches方法第一个参数是原密码，第二个参数是加密后的密码
        boolean matches = passwordEncoder.matches(password, newPassword);
        System.out.println("两个密码一致:" + matches);

    }

    public static String md5(String text) {
        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }

        try {
            msgDigest.update(text.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("System doesn't support your EncodingException.");

        }

        byte[] bytes = msgDigest.digest();
        return new String(encodeHex(bytes));
    }

    private static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return out;
    }

}
