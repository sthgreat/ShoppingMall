package com.dzkjdx.mall.service;

import com.dzkjdx.mall.MallApplicationTests;
import com.dzkjdx.mall.enums.ResponseEnum;
import com.dzkjdx.mall.service.impl.OrderServiceImpl;
import com.dzkjdx.mall.vo.OrderVo;
import com.dzkjdx.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class IOrderServiceTest extends MallApplicationTests {
    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void create() {
        ResponseVo<OrderVo> orderVoResponseVo = orderService.create(4, 9);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), orderVoResponseVo.getStatus());
    }
}