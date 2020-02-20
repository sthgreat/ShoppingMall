package com.dzkjdx.mall.service;

import com.dzkjdx.mall.MallApplicationTests;
import com.dzkjdx.mall.dao.CategoryMapper;
import com.dzkjdx.mall.enums.ResponseEnum;
import com.dzkjdx.mall.vo.CategoryVo;
import com.dzkjdx.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.ws.Response;

import java.util.List;

import static org.junit.Assert.*;

public class ICategoryServiceTest extends MallApplicationTests {

    @Autowired
    private ICategoryService categoryService;

    @Test
    public void getCategories() {
        ResponseVo<List<CategoryVo>> responseVo = categoryService.getCategories();
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}