package com.lwq.miaosha06.access;

import com.alibaba.fastjson.JSON;
import com.lwq.miaosha06.model.MiaoshaUser;
import com.lwq.miaosha06.redis.AccessKey;
import com.lwq.miaosha06.redis.RedisService;
import com.lwq.miaosha06.result.CodeMsg;
import com.lwq.miaosha06.result.Result;
import com.lwq.miaosha06.service.MiaoshaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author: Lwq
 * @Date: 2019/3/21 15:11
 * @Version 1.0
 * @Describe
 */
@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if(accessLimit==null){
                return true;
            }
            MiaoshaUser user = getUser(request, response);
            UserContext.setUser(user);

            int seconds = accessLimit.seconds();
            int counts = accessLimit.counts();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();

            if(needLogin){
                if(user==null){
                    render(response,CodeMsg.USER_NOT_EXIST);
                    return false;
                }
                key += "_"+user.getId();
            }
            Integer times = redisService.get(AccessKey.withExpireSecond(seconds), key, Integer.class);
            if(times==null){
                redisService.set(AccessKey.withExpireSecond(seconds), key,1);
            }else if(times<counts){
                redisService.incr(AccessKey.withExpireSecond(seconds), key);
            }else {
                render(response,CodeMsg.NOT_ACCESS);
                return false;
            }
        }
        return true;
    }

    private void render(HttpServletResponse response, CodeMsg cm) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(JSON.toJSONString(Result.error(cm)).getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }

    private MiaoshaUser getUser(HttpServletRequest request, HttpServletResponse response){
        String paramToken = request.getParameter(MiaoshaUserService.COOKI_NAME_TOKEN);
        String cookieToken = getCookieValue(request,MiaoshaUserService.COOKI_NAME_TOKEN);

        if(StringUtils.isEmpty(paramToken)&&StringUtils.isEmpty(cookieToken)){
            return null;
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return miaoshaUserService.getByTokan(response,token);
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if(cookies==null || cookies.length<=0){
            return null;
        }
        for(Cookie cookie:cookies){
            if(cookie.getName().equals(cookieName)){
                return cookie.getValue();
            }
        }
        return null;
    }
}
