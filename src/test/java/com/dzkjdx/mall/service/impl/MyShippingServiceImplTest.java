package com.dzkjdx.mall.service.impl;

import com.dzkjdx.mall.MallApplicationTests;
import com.dzkjdx.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

@Slf4j
public class MyShippingServiceImplTest extends MallApplicationTests {
    @Autowired
    private MyShippingServiceImpl shippingService;

    @Test
    public void list() {
        ResponseVo<PageInfo> list = shippingService.list(4, 2, 2);
        log.info("list:{}",list.toString());
    }
}