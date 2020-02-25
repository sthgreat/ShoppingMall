package com.dzkjdx.mall.dao;

import com.dzkjdx.mall.pojo.Order;
import org.springframework.stereotype.Component;

@Component
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}