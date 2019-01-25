package com.imooc.miaosha.service;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.vo.GoodsVo;

/**
 * TODO
 * luokai
 * 2019/1/10 0010 下午 5:42
 */
public interface MiaoshaService {

    OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo);

    long getMiaoshaResult(Long userId, long goodsId);

    String createMiaoshaPath(MiaoshaUser user, long goodsId);

    boolean checkPath(MiaoshaUser user, long goodsId, String path);
}
