package com.lwq.miaosha06.access;

import com.lwq.miaosha06.redis.AccessKey;
import com.lwq.miaosha06.result.CodeMsg;
import com.lwq.miaosha06.result.Result;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author: Lwq
 * @Date: 2019/3/21 15:08
 * @Version 1.0
 * @Describe
 */
@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {
    int seconds();
    int counts();
    boolean needLogin() default true;
}
