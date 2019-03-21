package com.lwq.miaosha06.redis;

/**
 * @Author: Lwq
 * @Date: 2019/3/18 13:48
 * @Version 1.0
 * @Describe
 */
public class UserKey extends BasePrefix {
    public UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
