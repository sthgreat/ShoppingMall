package com.dzkjdx.mall.dao;

import com.dzkjdx.mall.MallApplicationTests;
import com.dzkjdx.mall.pojo.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
public class CategoryMapperTest extends MallApplicationTests {

    @Autowired
    private CategoryMapper categoryMapper;


    @Test
    public void selectCategories() {
        List<Category> categories = categoryMapper.selectCategories();
        for(Category category : categories){
            log.info("category: "+ category.toString());
        }
    }
}