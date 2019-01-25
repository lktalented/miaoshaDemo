package com.imooc.miaosha.redis;

/**
 * TODO
 * luokai
 * 2019/1/8 0008 下午 5:49
 */
public class GoodKey extends BasePrefix{

    public GoodKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodKey getMiaoshaGoodStock = new GoodKey(0,"gs");
    public static GoodKey getGoodList = new GoodKey(60,"gl");

}
