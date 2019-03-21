package com.lwq.miaosha06.controller;

import com.lwq.miaosha06.model.MiaoshaUser;
import com.lwq.miaosha06.result.Result;
import com.lwq.miaosha06.service.GoodsService;
import com.lwq.miaosha06.service.MiaoshaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Lwq
 * @Date: 2019/3/19 12:37
 * @Version 1.0
 * @Describe
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/info")
    @ResponseBody
    public Result<MiaoshaUser> info(MiaoshaUser  user, Model model){
        return Result.success(user);
    }
}
