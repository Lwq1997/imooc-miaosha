package com.lwq.miaosha06.rabbitmq;

import com.lwq.miaosha06.model.MiaoshaOrder;
import com.lwq.miaosha06.model.MiaoshaUser;
import com.lwq.miaosha06.redis.RedisService;
import com.lwq.miaosha06.service.GoodsService;
import com.lwq.miaosha06.service.MiaoshaService;
import com.lwq.miaosha06.service.OrderService;
import com.lwq.miaosha06.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Lwq
 * @Date: 2019/3/20 19:56
 * @Version 1.0
 * @Describe
 */
@Service
public class MQReceiver {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQSender mqSender;

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message){
        log.info("receive message: "+message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message){
        log.info("receive Topic1 message: "+message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message){
        log.info("receive Topic2 message: "+message);
    }

    @RabbitListener(queues = MQConfig.FANOUT_QUEUE1)
    public void receiveFanout1(String message){
        log.info("receive Fanout1 message: "+message);
    }

    @RabbitListener(queues = MQConfig.FANOUT_QUEUE2)
    public void receiveFanout2(String message){
        log.info("receive Fanout2 message: "+message);
    }

    @RabbitListener(queues = MQConfig.HEADERS_QUEUE)
    public void receiveHeaders(byte[] message){
        log.info("receive Headers message: "+new String(message));
    }

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receiveMiaoshaMessage(String message){
        log.info("receive message: "+message);
        MiaoshaMessage mm = RedisService.stringToBean(message,MiaoshaMessage.class);
        MiaoshaUser user = mm.getUser();
        long goodsId = mm.getGoodsId();
        //看库存(因为只有一部分可以进入队列)
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock<=0){
            return;
        }
        //判断是否秒杀到
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order!=null){
            return;
        }
        //减库存秒杀
        miaoshaService.miaosha(user,goods);
    }
}
