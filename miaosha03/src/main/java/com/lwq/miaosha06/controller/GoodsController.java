package com.lwq.miaosha06.controller;

import com.lwq.miaosha06.model.MiaoshaUser;
import com.lwq.miaosha06.service.GoodsService;
import com.lwq.miaosha06.service.MiaoshaUserService;
import com.lwq.miaosha06.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author: Lwq
 * @Date: 2019/3/19 12:37
 * @Version 1.0
 * @Describe
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/to_list")
    public String toList(MiaoshaUser  user,Model model){
        model.addAttribute("user",user);
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodsList);

        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String toDetail(MiaoshaUser  user, Model model, @PathVariable("goodsId") long goodsId){
        model.addAttribute("user",user);
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goods);

        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStauts = 0;
        int remainSeconds = 0;

        if(now<startTime){
            miaoshaStauts = 0;
            remainSeconds = (int)(startTime-now)/1000;
        }else if (now>endTime){
            miaoshaStauts = 2;
            remainSeconds = -1;
        }else {
            miaoshaStauts = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStauts",miaoshaStauts);
        model.addAttribute("remainSeconds",remainSeconds);
        return "goods_detail";
    }

}
