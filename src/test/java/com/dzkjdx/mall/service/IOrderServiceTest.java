package com.dzkjdx.mall.service;

import com.dzkjdx.mall.MallApplicationTests;
import com.dzkjdx.mall.enums.ResponseEnum;
import com.dzkjdx.mall.service.impl.OrderServiceImpl;
import com.dzkjdx.mall.vo.OrderVo;
import com.dzkjdx.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
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

    @Test
    public void list(){
        ResponseVo<PageInfo> list = orderService.list(4, 1, 2);
        log.info("result={}",gson.toJson(list));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), list.getStatus());
    }

    @Test
    public void detail(){
        ResponseVo<OrderVo> list = orderService.detail(4, Long.valueOf("1582621641893"));
        log.info("result={}",gson.toJson(list));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), list.getStatus());

    }
}