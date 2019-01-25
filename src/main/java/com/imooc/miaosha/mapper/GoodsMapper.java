package com.imooc.miaosha.mapper;

import com.imooc.miaosha.domain.MiaoshaGoods;
import com.imooc.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * TODO
 * luokai
 * 2019/1/10 0010 下午 2:27
 */

@Mapper
public interface GoodsMapper {

    /**
     * 参加秒杀的商品列表
     * @return
     */
    @Select("select g.*,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    List<GoodsVo> listGoodsVo();

    /**
     * 通过商品ID，查询商品信息
     * @param goodsId
     * @return
     */
    @Select("select g.*,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId} ")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);


    /**
     * 更新秒杀商品表中的秒杀库存数量
     * @param goodsId
     * @return
     */
    @Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
    int reduceStock(@Param("goodsId") long goodsId);

}
