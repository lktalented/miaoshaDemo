package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.util.UUIDUtil;
import com.imooc.miaosha.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 登录controller
 * luokai
 * 2019/1/8 0008 下午 3:34
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result doLogin(HttpServletResponse response, @Valid LoginVO loginVO){
        log.info(loginVO.toString());

        UsernamePasswordToken utoken = new UsernamePasswordToken(loginVO.getMobile(),loginVO.getPassword());
        Subject subject = SecurityUtils.getSubject();
        //完成登录
        subject.login(utoken);
        //获取用户信息
        MiaoshaUser miaoshaUser = (MiaoshaUser) subject.getPrincipal();
        //MiaoshaUser miaoshaUser = miaoshaUserService.login(loginVO);
        //生成cookie，通过cookie将 登录用户信息存入redis，实现分布式session
        String token = UUIDUtil.uuid();
        miaoshaUserService.addCookie(response,token,miaoshaUser);
        return Result.success(true);
    }

}
