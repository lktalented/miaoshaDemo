package com.imooc.miaosha.shiro;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * shiroRealm
 * luokai
 * 2019/1/9 0009 上午 11:14
 */
@Slf4j
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private MiaoshaUserService userService;

    /**
     * 用户登录验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("平台登录信息==========================>");
        LoginVO  loginVO = new LoginVO();
        UsernamePasswordToken tok = (UsernamePasswordToken)token;
        loginVO.setMobile(tok.getUsername());
        loginVO.setPassword(String.valueOf(tok.getPassword()));
        MiaoshaUser miaoshaUser = userService.login(loginVO);
        return new SimpleAuthenticationInfo(miaoshaUser,String.valueOf(tok.getPassword()),tok.getUsername());
    }

    /**
     * 当前用户授权（角色权限初始化）
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }
}
