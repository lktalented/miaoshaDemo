package com.imooc.miaosha.redis;

/**
 * luokai
 * 2019/1/8 0008 下午 5:41
 */
public class BasePrefix implements KeyPrefix{

    private int expireSeconds;

    private String prefix;

    public BasePrefix(String prefix){
        this(0,prefix);
    }

    public BasePrefix(int expireSeconds, String prefix){
       this.expireSeconds = expireSeconds;
       this.prefix = prefix;
    }
    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className +":" + prefix;
    }
}
