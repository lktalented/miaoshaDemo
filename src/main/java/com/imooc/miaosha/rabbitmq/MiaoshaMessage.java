package com.imooc.miaosha.rabbitmq;

import com.imooc.miaosha.domain.MiaoshaUser;
import lombok.Data;

/**
 * luokai
 * 2019/1/16 0016 下午 2:57
 */
@Data
public class MiaoshaMessage {

    private MiaoshaUser user;

    private long goosId;
}
