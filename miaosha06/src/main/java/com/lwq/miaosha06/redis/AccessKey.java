package com.lwq.miaosha06.redis;

/**
 * @Author: Lwq
 * @Date: 2019/3/18 13:48
 * @Version 1.0
 * @Describe
 */
public class AccessKey extends BasePrefix {
    public AccessKey(int expireSeconds,String prefix) {
        super(expireSeconds,prefix);
    }

    public static AccessKey withExpireSecond(int expireSeconds){
        return new AccessKey(expireSeconds,"access");
    }
}
