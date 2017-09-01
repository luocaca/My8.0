package com.example.administrator.my80;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class HeadManager {

    static String keyStr = "hmeg_api_~!@*(hmeg.cn";

    public static String getKeyStr(long current_time) {
        String Key = "";
        String Salt = current_time + "";
        Salt = Md(Salt, false);
        Key = Salt + Md((keyStr + Salt), true);
        return Key;
    }



    public static final String Md(String plainText, boolean judgeMD) {
        StringBuffer buf = new StringBuffer("");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            // System.out.println("32位：result: " + buf.toString());//32位的加密
            // System.out.println("16位：result: " +
            // buf.toString().substring(8,24));//16位的加密

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (judgeMD == true) {
            return buf.toString();
        } else {
            return buf.toString().substring(8, 24);
        }

    }

}
