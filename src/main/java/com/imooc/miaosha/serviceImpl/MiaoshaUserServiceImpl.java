package com.imooc.miaosha.serviceImpl;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.exception.GlobalException;
import com.imooc.miaosha.mapper.MiaoshaUserMapper;
import com.imooc.miaosha.redis.MiaoshaUserKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.util.MD5Util;
import com.imooc.miaosha.util.UUIDUtil;
import com.imooc.miaosha.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 秒杀用户类的实现方法
 * luokai
 * 2019/1/8 0008 下午 3:31
 */

@Slf4j
@Service
public class MiaoshaUserServiceImpl implements MiaoshaUserService {


    @Autowired
    private MiaoshaUserMapper miaoshaUserMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public MiaoshaUser getById(long id) {
        //取缓存
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, "" + id, MiaoshaUser.class);
        if (user != null){
            return user;
        }
        //取数据库
        user = miaoshaUserMapper.getById(id);
        if (user != null){
            redisService.set(MiaoshaUserKey.getById,"" + id,user);
        }
        return user;
        //return miaoshaUserMapper.getById(id);
    }

    public boolean updatePassword(String token,long id,String formPass){
        //取user
        MiaoshaUser user = this.getById(id);
        if (user != null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST) ;
        }
        //更新数据库
        MiaoshaUser newUser = new MiaoshaUser();
        newUser.setId(user.getId());
        newUser.setPassword(MD5Util.formPassToDBPass(formPass,user.getSalt()));
        miaoshaUserMapper.update(newUser);
        //处理缓存
        redisService.delete(MiaoshaUserKey.getById,""+id);
        user.setPassword(newUser.getPassword());
        redisService.set(MiaoshaUserKey.token,token,user);
        return true;
    }

    @Override
    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        //延长有效期
        if (user != null){
            addCookie(response,token,user);
        }
        return user;
    }

    @Override
    public MiaoshaUser login( LoginVO loginVO) {
        if (loginVO == null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVO.getMobile();
        String formPass = loginVO.getPassword();

        MiaoshaUser miaoshaUser = this.getById(Long.parseLong(mobile));
        if (miaoshaUser == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = miaoshaUser.getPassword();
        String saltDB = miaoshaUser.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass,saltDB);
        if (!calcPass.equals(dbPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
//        String token = UUIDUtil.uuid();
//        addCookie(miaoshaUser);
        return miaoshaUser;
    }

    @Override
    public void addCookie(HttpServletResponse response, String token, MiaoshaUser miaoshaUser) {
        redisService.set(MiaoshaUserKey.token,token,miaoshaUser);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);

    }


}
