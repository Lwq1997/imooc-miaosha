package com.lwq.miaosha06.redis;

/**
 * @Author: Lwq
 * @Date: 2019/3/18 13:45
 * @Version 1.0
 * @Describe
 */
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();
}
