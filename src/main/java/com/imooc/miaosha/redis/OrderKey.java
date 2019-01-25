package com.imooc.miaosha.redis;

/**
 * TODO
 * luokai
 * 2019/1/8 0008 下午 5:49
 */
public class OrderKey extends BasePrefix{

    public OrderKey( String prefix) {
        super(prefix);
    }

    public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");

}
