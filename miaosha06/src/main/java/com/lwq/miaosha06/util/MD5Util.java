package com.lwq.miaosha06.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author: Lwq
 * @Date: 2019/3/19 10:26
 * @Version 1.0
 * @Describe
 */
public class MD5Util {

    private static final String salt = "1a2b3c4d";

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }


    public static String inputPassToFromPass(String inputPass){
        String str = ""+salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    public static String fromPassToDBPass(String fromPass,String saltDB){
        String str = ""+saltDB.charAt(0)+saltDB.charAt(2)+fromPass+saltDB.charAt(5)+saltDB.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass,String saltDB){
        return fromPassToDBPass(inputPassToFromPass(inputPass),saltDB);
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFromPass("123456"));
        System.out.println(fromPassToDBPass(inputPassToFromPass("123456"),"1a2b3c4d"));
        System.out.println(inputPassToDBPass("123456","1a2b3c4d"));
    }
}
