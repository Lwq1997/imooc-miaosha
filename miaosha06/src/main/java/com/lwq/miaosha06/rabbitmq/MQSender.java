package com.lwq.miaosha06.rabbitmq;

import com.lwq.miaosha06.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Lwq
 * @Date: 2019/3/20 19:55
 * @Version 1.0
 * @Describe
 */
@Service
public class MQSender {

    private static Logger log = LoggerFactory.getLogger(MQSender.class);


    @Autowired
    AmqpTemplate amqpTemplate;

    public void send(Object object){
        String message = RedisService.beanToString(object);
        log.info("send message: "+ message);
        amqpTemplate.convertAndSend(MQConfig.QUEUE,message);
    }

    public void sendTopic(Object object){
        String message = RedisService.beanToString(object);
        log.info("send Topic message: "+ message);
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,MQConfig.ROUTE_KEY1,message+"1");
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,MQConfig.ROUTE_KEY2,message+"2");
    }

    public void sendFanout(Object object){
        String message = RedisService.beanToString(object);
        log.info("send Fanout message: "+ message);
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE,"",message);
    }

    public void sendHeader(Object object){
        String message = RedisService.beanToString(object);
        log.info("send Headers message: "+ message);
        MessageProperties properties = new MessageProperties();
        properties.setHeader("header1","value1");
        properties.setHeader("header2","value2");
        Message obj = new Message(message.getBytes(),properties);
        amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE,"",obj);
    }

    public void sendMiaoshaMessage(MiaoshaMessage mm) {
        String message = RedisService.beanToString(mm);
        log.info("send message: "+ message);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE,message);
    }
}
