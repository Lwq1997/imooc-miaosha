package com.lwq.miaosha06.controller;

import com.lwq.miaosha06.result.Result;
import com.lwq.miaosha06.service.MiaoshaUserService;
import com.lwq.miaosha06.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
 * @Author: Lwq
 * @Date: 2019/3/19 10:39
 * @Version 1.0
 * @Describe
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result doLogin(HttpServletResponse response,@Valid LoginVo loginVo){
        log.info(loginVo.toString());
        //登录
        miaoshaUserService.login(response,loginVo);
        return Result.success(true);
    }
}
