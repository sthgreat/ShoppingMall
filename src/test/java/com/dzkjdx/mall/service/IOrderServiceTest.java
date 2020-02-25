package com.dzkjdx.mall.service;

import com.dzkjdx.mall.MallApplicationTests;
import com.dzkjdx.mall.enums.ResponseEnum;
import com.dzkjdx.mall.service.impl.OrderServiceImpl;
import com.dzkjdx.mall.vo.OrderVo;
import com.dzkjdx.mall.vo.ResponseVo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

@Slf4j
public class IOrderServiceTest extends MallApplicationTests {
    @Autowired
    private OrderServiceImpl orderService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void create() {
        ResponseVo<OrderVo> orderVoResponseVo = orderService.create(4, 9);
        log.info("result={}",gson.toJson(orderVoResponseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), orderVoResponseVo.getStatus());

    }
}