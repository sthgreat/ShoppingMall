package com.dzkjdx.mall.dao;

import com.dzkjdx.mall.pojo.Shipping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    List<Shipping> selectByUid(Integer uid);

    Shipping selectByUidAndShippingId(Integer uid, Integer shippingId);
}