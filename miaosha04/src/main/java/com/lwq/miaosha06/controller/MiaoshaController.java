package com.lwq.miaosha06.controller;

import com.lwq.miaosha06.model.MiaoshaOrder;
import com.lwq.miaosha06.model.MiaoshaUser;
import com.lwq.miaosha06.model.OrderInfo;
import com.lwq.miaosha06.result.CodeMsg;
import com.lwq.miaosha06.result.Result;
import com.lwq.miaosha06.service.GoodsService;
import com.lwq.miaosha06.service.MiaoshaService;
import com.lwq.miaosha06.service.OrderService;
import com.lwq.miaosha06.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Lwq
 * @Date: 2019/3/19 17:00
 * @Version 1.0
 * @Describe
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RequestMapping(value = "/do_miaosha",method = RequestMethod.POST)
    @ResponseBody
    public Result<OrderInfo> list(Model model, MiaoshaUser user, @RequestParam("goodsId")long goodsId){

        model.addAttribute("user",user);
        if(user==null){
            return Result.error(CodeMsg.USER_NOT_EXIST);
        }
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock<=0){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        model.addAttribute("erring", CodeMsg.MIAO_SHA_OVER);
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order!=null){
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        OrderInfo orderInfo = miaoshaService.miaosha(user,goods);
        return Result.success(orderInfo);
    }


}
