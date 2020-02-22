package com.dzkjdx.mall.dao;

import com.dzkjdx.mall.pojo.Shipping;
import org.springframework.stereotype.Component;

@Component
public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);
}