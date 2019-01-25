package com.imooc.miaosha.redis;

/**
 * TODO
 * luokai
 * 2019/1/8 0008 下午 5:49
 */
public class MiaoshaUserKey extends BasePrefix{

    public static final int TOKEN_EXPIRE = 3600*2;

    public  MiaoshaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public  static MiaoshaUserKey getById  = new MiaoshaUserKey(0,"id");

    public static  MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"tk");
}
