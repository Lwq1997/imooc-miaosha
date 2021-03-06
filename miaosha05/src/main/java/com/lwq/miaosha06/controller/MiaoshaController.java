package com.lwq.miaosha06.controller;

import com.lwq.miaosha06.model.MiaoshaOrder;
import com.lwq.miaosha06.model.MiaoshaUser;
import com.lwq.miaosha06.rabbitmq.MQSender;
import com.lwq.miaosha06.rabbitmq.MiaoshaMessage;
import com.lwq.miaosha06.redis.GoodsKey;
import com.lwq.miaosha06.redis.MiaoshaKey;
import com.lwq.miaosha06.redis.OrderKey;
import com.lwq.miaosha06.redis.RedisService;
import com.lwq.miaosha06.result.CodeMsg;
import com.lwq.miaosha06.result.Result;
import com.lwq.miaosha06.service.GoodsService;
import com.lwq.miaosha06.service.MiaoshaService;
import com.lwq.miaosha06.service.OrderService;
import com.lwq.miaosha06.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Lwq
 * @Date: 2019/3/19 17:00
 * @Version 1.0
 * @Describe
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

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

    Map<Long,Boolean> localOverMap = new HashMap<>();
    
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        if(goodsVoList==null){
            return;
        }
        for(GoodsVo goods:goodsVoList){
            redisService.set(GoodsKey.getMiaoshaGoodsStock,""+goods.getId(),goods.getStockCount());
            localOverMap.put(goods.getId(),false);
        }
    }

    @RequestMapping(value = "/do_miaosha",method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> miaosha(Model model, MiaoshaUser user, @RequestParam("goodsId")long goodsId){

        model.addAttribute("user",user);
        if(user==null){
            return Result.error(CodeMsg.USER_NOT_EXIST);
        }
        //内存标记,减少redis访问
        Boolean over = localOverMap.get(goodsId);
        if(over){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        Long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);
        if(stock<0){
            localOverMap.put(goodsId,true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否秒杀到
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order!=null){
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        //入队
        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setUser(user);
        mm.setGoodsId(goodsId);
        mqSender.sendMiaoshaMessage(mm);
        return Result.success(0);
        /*
        //看库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock<=0){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否秒杀到
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order!=null){
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        //减库存秒杀
        OrderInfo orderInfo = miaoshaService.miaosha(user,goods);
        return Result.success(orderInfo);*/
    }

    /**
     * orderId:成功
     * 1：失败
     * 0：排队中
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/result",method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoshaResult(Model model, MiaoshaUser user, @RequestParam("goodsId")long goodsId){

        model.addAttribute("user",user);
        if(user==null){
            return Result.error(CodeMsg.USER_NOT_EXIST);
        }

        long result = miaoshaService.getMiaoshaResult(user.getId(),goodsId);

        return Result.success(result);
    }

    /**
     * 还原秒杀前
     * @param model
     * @return
     */
    @RequestMapping(value="/reset", method=RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> reset(Model model) {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        for(GoodsVo goods : goodsList) {
            goods.setStockCount(10);
            redisService.set(GoodsKey.getMiaoshaGoodsStock, ""+goods.getId(), 10);
            localOverMap.put(goods.getId(), false);
        }
        redisService.delete(OrderKey.getOrderByUidGid);
        redisService.delete(MiaoshaKey.isGoodsOver);
        miaoshaService.reset(goodsList);
        return Result.success(true);
    }

}
