package com.lwq.miaosha06.access;

import com.lwq.miaosha06.model.MiaoshaUser;

/**
 * @Author: Lwq
 * @Date: 2019/3/21 15:19
 * @Version 1.0
 * @Describe
 */
public class UserContext {
    private static ThreadLocal<MiaoshaUser> userThreadLocal = new ThreadLocal<>();

    public static void setUser(MiaoshaUser user){
        userThreadLocal.set(user);
    }

    public static MiaoshaUser getUser(){
        return userThreadLocal.get();
    }
}
