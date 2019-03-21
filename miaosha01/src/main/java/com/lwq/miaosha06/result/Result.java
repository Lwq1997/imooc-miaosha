package com.lwq.miaosha06.result;

import lombok.Getter;

/**
 * @Author: Lwq
 * @Date: 2019/3/17 21:25
 * @Version 1.0
 * @Describe
 */
@Getter
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    public Result(T data) {
        this.code = 200;
        this.msg = "success";
        this.data = data;
    }

    /**
     * 成功时候的调用
     * */
    public static <T> Result<T> success(T data){
        return new  Result<T>(data);
    }

    /**
     * 失败时候的调用
     * */
    public static <T> Result<T> error(CodeMsg cm){
        return new  Result<T>(cm);
    }

    private Result(CodeMsg cm) {
        if(cm == null) {
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }


}
