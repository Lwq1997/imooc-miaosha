package com.lwq.miaosha06.redis;

/**
 * @Author: Lwq
 * @Date: 2019/3/18 13:48
 * @Version 1.0
 * @Describe
 */
public class OrderKey extends BasePrefix {
    public OrderKey(String prefix) {
        super(prefix);
    }

    public static OrderKey getOrderByUidGid = new OrderKey("moug");
}
