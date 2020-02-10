package com.dzkjdx.mall;

import com.dzkjdx.mall.dao.CategoryMapper;
import com.dzkjdx.mall.pojo.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallApplicationTests {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void contextLoads() {
        Category byName = categoryMapper.findByName(100001);
        System.out.print(byName.toString());
    }

}
