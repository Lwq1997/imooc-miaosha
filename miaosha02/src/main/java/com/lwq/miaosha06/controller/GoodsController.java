package com.lwq.miaosha06.controller;

import com.lwq.miaosha06.model.MiaoshaUser;
import com.lwq.miaosha06.service.MiaoshaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/to_list")
    public String toList(MiaoshaUser  user,Model model){
        model.addAttribute("user",user);
        return "goods_list";
    }
}
