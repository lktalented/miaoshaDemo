package com.imooc.miaosha.redis;

/**
 * luokai
 * 2019/8/6 0006 下午 5:15
 */
public class AccessKey extends BasePrefix{

    public AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public  static  AccessKey withExpire(int expireSeconds){
        return new AccessKey(expireSeconds,"access");
    }
}
