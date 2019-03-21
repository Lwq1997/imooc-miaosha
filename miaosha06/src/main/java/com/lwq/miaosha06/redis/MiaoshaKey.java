package com.lwq.miaosha06.redis;

/**
 * @Author: Lwq
 * @Date: 2019/3/18 13:48
 * @Version 1.0
 * @Describe
 */
public class MiaoshaKey extends BasePrefix {
    public MiaoshaKey(int expireSeconds,String prefix) {
        super(expireSeconds,prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey(0,"go");
    public static MiaoshaKey MiaoshaPath = new MiaoshaKey(60,"mp");
    public static MiaoshaKey getMiaoshaVerifyCode = new MiaoshaKey(300,"vc");
}
