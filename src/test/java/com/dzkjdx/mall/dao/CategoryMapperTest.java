package com.dzkjdx.mall.dao;

import com.dzkjdx.mall.MallApplicationTests;
import com.dzkjdx.mall.pojo.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

public class CategoryMapperTest extends MallApplicationTests {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void findById() {
        Category byName = categoryMapper.findById(100001);
        System.out.print(byName.toString());
    }

    @Test
    public void queryById() {
        Category byId = categoryMapper.queryById(100001);
        System.out.print(byId.toString());
    }
}