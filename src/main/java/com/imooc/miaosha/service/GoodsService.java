package com.imooc.miaosha.service;

import com.imooc.miaosha.vo.GoodsVo;

import java.util.List;

/**
 * luokai
 * 2019/1/10 0010 下午 2:42
 */
public interface GoodsService {

    List<GoodsVo> listGoodsVo();

    GoodsVo getGoodsVoByGoodsId(long goodsId);

    Boolean reduceStock(GoodsVo goods);
}
