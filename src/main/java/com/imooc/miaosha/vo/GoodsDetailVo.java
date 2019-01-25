package com.imooc.miaosha.vo;

import com.imooc.miaosha.domain.MiaoshaUser;
import lombok.Data;

/**
 * 商品详情
 * luokai
 * 2019/1/14 0014 下午 4:07
 */
@Data
public class GoodsDetailVo {
    private int miaoshaStatus;

    private int remainSeconds;

    private GoodsVo goods;

    private MiaoshaUser user;
}
