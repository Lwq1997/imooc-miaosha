package com.lwq.miaosha06.dao;

import com.lwq.miaosha06.model.MiaoshaOrder;
import com.lwq.miaosha06.model.OrderInfo;
import org.apache.ibatis.annotations.*;

/**
 * @Author: Lwq
 * @Date: 2019/3/19 17:11
 * @Version 1.0
 * @Describe
 */
@Mapper
public interface OrderDao {

    @Select("select * from miaosha_order where user_id=#{userId} and goods_id=#{goodsId}")
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId") Long userId, @Param("goodsId") Long goodsId);

    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    public long insert(OrderInfo orderInfo);

    @Insert("insert into miaosha_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    public int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    @Select("select * from order_info where id = #{orderId}")
    OrderInfo getOrderById(long orderId);


    @Delete("delete from order_info")
    public void deleteOrders();

    @Delete("delete from miaosha_order")
    public void deleteMiaoshaOrders();
}
