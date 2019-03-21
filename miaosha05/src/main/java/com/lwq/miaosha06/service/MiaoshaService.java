package com.lwq.miaosha06.service;

import com.lwq.miaosha06.model.MiaoshaOrder;
import com.lwq.miaosha06.model.MiaoshaUser;
import com.lwq.miaosha06.model.OrderInfo;
import com.lwq.miaosha06.redis.MiaoshaKey;
import com.lwq.miaosha06.redis.RedisService;
import com.lwq.miaosha06.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    RedisService redisService;

    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
//       减库存
        boolean success = goodsService.reduceStock(goods);
//        下订单
        if (success){
            return orderService.createOrder(user,goods);
        }else {
            setGoodsOver(goods.getId());
            return null;
        }
    }


    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        if(order!=null){
            return order.getOrderId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if(isOver){
                return 1;
            }else {
                return 0;
            }
        }
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.isGoodsOver,""+goodsId);
    }


    private void setGoodsOver(Long id) {
        redisService.set(MiaoshaKey.isGoodsOver,""+id,true);
    }


    public void reset(List<GoodsVo> goodsList) {
        goodsService.resetStock(goodsList);
        orderService.deleteOrders();
    }
}
