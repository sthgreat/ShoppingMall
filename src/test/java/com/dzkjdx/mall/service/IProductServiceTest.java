package com.dzkjdx.mall.service;

import com.dzkjdx.mall.MallApplicationTests;
import com.dzkjdx.mall.consts.MallConst;
import com.dzkjdx.mall.enums.ResponseEnum;
import com.dzkjdx.mall.service.impl.ProductServiceImpl;
import com.dzkjdx.mall.vo.ProductVo;
import com.dzkjdx.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class IProductServiceTest extends MallApplicationTests {
    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void list() {
        ResponseVo<List<ProductVo>> responseVo = productService.list(100002, 1, 1);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}