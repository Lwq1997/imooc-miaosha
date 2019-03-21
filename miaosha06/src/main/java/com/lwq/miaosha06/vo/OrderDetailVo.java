package com.lwq.miaosha06.vo;

import com.lwq.miaosha06.model.OrderInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: Lwq
 * @Date: 2019/3/20 15:12
 * @Version 1.0
 * @Describe
 */
@Getter
@Setter
@ToString
public class OrderDetailVo {

    private GoodsVo goods;
    private OrderInfo order;
}
