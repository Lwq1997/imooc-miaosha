package com.lwq.miaosha06.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Lwq
 * @Date: 2019/3/17 21:23
 * @Version 1.0
 * @Describe
 */
@Getter
@AllArgsConstructor
public class CodeMsg {

    private int code;
    private String msg;

    //通用异常
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    //登录模块 5002XX

    //商品模块 5003XX

    //订单模块 5004XX

    //秒杀模块 5005XX
}
