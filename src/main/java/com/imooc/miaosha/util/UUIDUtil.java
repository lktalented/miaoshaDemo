package com.imooc.miaosha.util;

import java.util.UUID;

/**
 * 
 * luokai
 * 2019/1/8 0008 下午 5:01
 */
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-","");
    }
}
