package com.lwq.miaosha06.redis;

/**
 * @Author: Lwq
 * @Date: 2019/3/19 12:31
 * @Version 1.0
 * @Describe
 */
public class MiaoshaUserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600*24*2;
    public MiaoshaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }


    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"tk");


}
