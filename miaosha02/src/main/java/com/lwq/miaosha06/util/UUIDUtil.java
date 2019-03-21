package com.lwq.miaosha06.util;

import java.util.UUID;

/**
 * @Author: Lwq
 * @Date: 2019/3/19 12:29
 * @Version 1.0
 * @Describe
 */
public class UUIDUtil {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
