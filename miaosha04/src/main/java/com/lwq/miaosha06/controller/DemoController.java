package com.lwq.miaosha06.controller;

import com.lwq.miaosha06.model.User;
import com.lwq.miaosha06.redis.RedisService;
import com.lwq.miaosha06.redis.UserKey;
import com.lwq.miaosha06.result.CodeMsg;
import com.lwq.miaosha06.result.Result;
import com.lwq.miaosha06.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Lwq
 * @Date: 2019/3/17 21:21
 * @Version 1.0
 * @Describe
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello lwq";
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name","lwq");
        return "hello";
    }

    @RequestMapping("/helloSuccess")
    @ResponseBody
    public Result<String> helloSuccess(){
        return Result.success("hello lwq");
    }

    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String> helloError(){
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @RequestMapping("/getdb")
    @ResponseBody
    public Result<User> getDb(int id){
        User user = userService.getUserById(id);
        return Result.success(user);
    }

    @RequestMapping("/redis/set/id")
    @ResponseBody
    public Result<String> setRedisValueId(String key){
        User user = new User();
        user.setId(4);
        user.setName("lwq4");
        boolean isSuccess = redisService.set(UserKey.getById,key,user);
        if(isSuccess){
            return Result.success("key: "+UserKey.getById.getPrefix()+key+"  value: "+user);
        }else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    @RequestMapping("/redis/get/id")
    @ResponseBody
    public Result<User> getRedisValueId(String key){
        User user = redisService.get(UserKey.getById,key, User.class);
        return Result.success(user);
    }
}
