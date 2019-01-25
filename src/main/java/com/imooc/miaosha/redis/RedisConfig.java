package com.imooc.miaosha.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * redis配置类
 * luokai
 * 2019/1/8 0008 下午 5:20
 */

@Component
@ConfigurationProperties(prefix = "redis")
@Data
public class RedisConfig {

    private String host;

    private int port;

    private int timeout;//秒

    private String password;

    private int poolMaxTotal;

    private int poolMaxIdle;

    private int poolMaxWait;//秒
}
