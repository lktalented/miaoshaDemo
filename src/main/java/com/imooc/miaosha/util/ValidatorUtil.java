package com.imooc.miaosha.util;


import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参数验证类
 * 2019/1/8 0008 下午 3:14
 */
public class ValidatorUtil {

    private static final Pattern MOBILE_PATTERN = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String value){
        if (StringUtils.isEmpty(value)){
            return false;
        }
        Matcher matcher = MOBILE_PATTERN.matcher(value);
        return matcher.matches();
    }
}
