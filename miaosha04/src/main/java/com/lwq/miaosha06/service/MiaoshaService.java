package com.lwq.miaosha06.service;

import com.lwq.miaosha06.model.MiaoshaUser;
import com.lwq.miaosha06.model.OrderInfo;
import com.lwq.miaosha06.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Lwq
 * @Date: 2019/3/19 17:15
 * @Version 1.0
 * @Describe
 */
@Service
public class MiaoshaService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
//       减库存
        goodsService.reduceStock(goods);
//        下订单
       return orderService.createOrder(user,goods);

    }
}
