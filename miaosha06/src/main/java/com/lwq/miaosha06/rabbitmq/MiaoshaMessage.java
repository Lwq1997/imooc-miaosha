package com.lwq.miaosha06.rabbitmq;

import com.lwq.miaosha06.model.MiaoshaUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: Lwq
 * @Date: 2019/3/20 21:11
 * @Version 1.0
 * @Describe
 */
@Getter
@Setter
@ToString
public class MiaoshaMessage {

    private MiaoshaUser user;
    private long goodsId;
}
