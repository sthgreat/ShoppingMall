package com.dzkjdx.mall.service;

import com.dzkjdx.mall.MallApplicationTests;
import com.dzkjdx.mall.consts.MallConst;
import com.dzkjdx.mall.enums.ResponseEnum;
import com.dzkjdx.mall.service.impl.ProductServiceImpl;
import com.dzkjdx.mall.vo.ProductDetailVo;
import com.dzkjdx.mall.vo.ProductVo;
import com.dzkjdx.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

@Slf4j
public class IProductServiceTest extends MallApplicationTests {
    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void list() {
        ResponseVo<PageInfo> responseVo = productService.list(null, 1, 1);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void detail(){
        ResponseVo<ProductDetailVo> responseVo = productService.selectByProductId(100006);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}