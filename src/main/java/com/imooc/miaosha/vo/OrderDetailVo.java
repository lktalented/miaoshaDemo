package com.imooc.miaosha.vo;

import com.imooc.miaosha.domain.OrderInfo;
import lombok.Data;

/**
 * TODO
 * luokai
 * 2019/1/14 0014 下午 6:38
 */
@Data
public class OrderDetailVo {

    private GoodsVo goods;

    private OrderInfo order;
}
