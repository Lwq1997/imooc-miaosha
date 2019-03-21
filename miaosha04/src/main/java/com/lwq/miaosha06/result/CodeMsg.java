package com.lwq.miaosha06.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author: Lwq
 * @Date: 2019/3/17 21:23
 * @Version 1.0
 * @Describe
 */
@Getter
@AllArgsConstructor
@ToString
public class CodeMsg {



    private int code;
    private String msg;

    public static CodeMsg SUCCESS = new CodeMsg(0, "成功");


    //通用异常
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
     //登录模块 5002XX
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500201, "密码为空");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500202, "密码错误");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500203, "手机号为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500204, "手机号格式错误");
    public static CodeMsg USER_NOT_EXIST = new CodeMsg(500205, "用户不存在");


    //商品模块 5003XX

    //订单模块 5004XX
    public static CodeMsg ORDER_NOT_EXIST =new CodeMsg(500400, "订单不存在");


    //秒杀模块 5005XX
    public static CodeMsg MIAO_SHA_OVER = new CodeMsg(500500,"秒杀库存不足");
    public static CodeMsg REPEATE_MIAOSHA = new CodeMsg(500501,"重复秒杀");

    public CodeMsg fillArgs(Object... args){
        int code = this.code;
        String message = String.format(this.msg,args);
        return new CodeMsg(code,message);
    }
}
