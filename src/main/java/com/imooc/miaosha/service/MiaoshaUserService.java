package com.imooc.miaosha.service;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.vo.LoginVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * luokai
 * 2019/1/8 0008 下午 2:54
 */
public interface MiaoshaUserService {

    String COOKIE_NAME_TOKEN = "token";

    MiaoshaUser getById(long id);

    MiaoshaUser login (LoginVO loginVO );

    void addCookie(HttpServletResponse response,String token, MiaoshaUser miaoshaUser);

    MiaoshaUser getByToken(HttpServletResponse response, String token);
}
