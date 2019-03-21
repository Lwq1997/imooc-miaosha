package com.lwq.miaosha06.service;

import com.lwq.miaosha06.dao.MiaoshaUserDao;
import com.lwq.miaosha06.exception.GlobalException;
import com.lwq.miaosha06.model.MiaoshaUser;
import com.lwq.miaosha06.redis.MiaoshaUserKey;
import com.lwq.miaosha06.redis.RedisService;
import com.lwq.miaosha06.result.CodeMsg;
import com.lwq.miaosha06.util.MD5Util;
import com.lwq.miaosha06.util.UUIDUtil;
import com.lwq.miaosha06.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Lwq
 * @Date: 2019/3/19 11:03
 * @Version 1.0
 * @Describe
 */
@Service
public class MiaoshaUserService {

    public static final String COOKI_NAME_TOKEN = "token";

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;

    public MiaoshaUser getUserById(long id){
        return miaoshaUserDao.getById(id);
    }

    public boolean login(HttpServletResponse response,LoginVo loginVo){
        if(loginVo==null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String fromPass = loginVo.getPassword();

        MiaoshaUser user = getUserById(Long.parseLong(mobile));

        if(user==null){
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String salt = user.getSalt();
        String calcPass = MD5Util.fromPassToDBPass(fromPass, salt);
        if(!calcPass.equals(dbPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return true;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public MiaoshaUser getByTokan(HttpServletResponse response,String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token,token,MiaoshaUser.class);
        if(user!=null){
            //延长有效期
            addCookie(response, token, user);
            return user;
        }
        return null;
    }
}
