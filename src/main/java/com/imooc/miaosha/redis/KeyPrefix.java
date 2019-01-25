package com.imooc.miaosha.redis;

/**
 * 前缀接口
 * luokai
 * 2019/1/8 0008 下午 5:39
 */
public interface KeyPrefix {

    int expireSeconds();

    String getPrefix();
}
