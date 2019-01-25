package com.imooc.miaosha.service;

import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.vo.GoodsVo;

/**
 * luokai
 * 2019/1/10 0010 下午 6:00
 */
public interface OrderService {

    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId,Long goodsId);

    OrderInfo createOrder(MiaoshaUser user, GoodsVo goods);

    OrderInfo getOrderById(long orderId);
}
