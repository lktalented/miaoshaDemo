package com.imooc.miaosha.mapper;

import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.OrderInfo;
import org.apache.ibatis.annotations.*;

/**
 * luokai
 * 2019/1/10 0010 下午 4:22
 */

@Mapper
public interface OrderMapper {

    /**
     * 通过用户ID，订单ID查询此秒杀订单是否存在
     * @param userId
     * @param goodsId
     * @return
     */
    @Select("select * from miaosha_order where user_id = #{userId} and goods_id = #{goodsId} ")
    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId (@Param("userId")long userId,@Param("goodsId")long goodsId );

    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)" +
            "values(#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn = "id", keyProperty = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    long insert(OrderInfo orderInfo);

    @Insert("insert into miaosha_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    @Select("select * from order_info where id = #{orderId}")
    OrderInfo getOrderById(@Param("orderId")long orderId);

}
