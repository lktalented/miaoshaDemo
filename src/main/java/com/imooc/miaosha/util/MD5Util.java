package com.imooc.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * md5加密工具类
 * luokai
 * 2019/1/8 0008 下午 4:51
 */
public class MD5Util {

    /**
     * 将表单中的密码 生成 数据库中的密码（通过盐值加密）
     * @param formPass
     * @param saltDB
     * @return
     */
    public static String formPassToDBPass(String formPass, String saltDB) {
        String str = "" + saltDB.charAt(0) + saltDB.charAt(2) + formPass + saltDB.charAt(5) + saltDB.charAt(4);
        return md5(str);
    }

    public  static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

}
