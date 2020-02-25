package com.dzkjdx.mall.dao;

import com.dzkjdx.mall.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    int batchInsert(@Param("orderItemList") List<OrderItem> orderItemList);

    List<OrderItem> selectByOrderNoSet(@Param("orderNoSet") Set orderNoSet);
}