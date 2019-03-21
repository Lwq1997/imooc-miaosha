package com.lwq.miaosha06.redis;

/**
 * @Author: Lwq
 * @Date: 2019/3/18 13:48
 * @Version 1.0
 * @Describe
 */
public class GoodsKey extends BasePrefix {
    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static GoodsKey getGoodsList = new GoodsKey(120, "gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(120, "gd");
}
