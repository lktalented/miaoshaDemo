package com.imooc.miaosha.access;

import com.alibaba.fastjson.JSON;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.redis.AccessKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.MiaoshaUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 实现拦截器，在方法之前
 * luokai
 * 2019/8/6 0006 下午 4:40
 */

@Slf4j
@Service
public class AccessInteceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MiaoshaUserService userService;

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       if(handler instanceof HandlerMethod){
           MiaoshaUser user = getUser(request,response);
           UserContext.setUser(user);
           HandlerMethod hm = (HandlerMethod)handler;
           AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
           if (accessLimit == null){
               return true;
           }
           int seconds = accessLimit.seconds();
           int maxCount = accessLimit.maxCount();
           boolean needLogin = accessLimit.needLogin();
           String key = request.getRequestURI();
           if (needLogin){
               if (user == null){
                   render(response, CodeMsg.SESSION_ERROR);
                   return false;
               }
               key += "_" + user.getId();
           }else {

           }
           AccessKey ak = AccessKey.withExpire(seconds);
           Integer count = redisService.get(ak,key,Integer.class);
           if (count == null){
               redisService.set(ak,key,1);
           }else if (count < maxCount){
               redisService.incr(ak,key);
           }else {
              render(response,CodeMsg.ACCESS_LIMIT_REACHED);
              return false;
           }
       }
       return true;
    }

    private void render(HttpServletResponse response, CodeMsg cm) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(Result.error(cm));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    private MiaoshaUser getUser(HttpServletRequest request, HttpServletResponse response) {
        String paramToken = request.getParameter(MiaoshaUserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request,MiaoshaUserService.COOKIE_NAME_TOKEN);
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken) ){
            return null;
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return userService.getByToken(response,token);
    }

    private String getCookieValue(HttpServletRequest request, String cookiName) {
        Cookie[]  cookies = request.getCookies();
        if(cookies == null || cookies.length <= 0){
            return null;
        }
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookiName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
