package com.dzkjdx.mall.service.impl;

import com.dzkjdx.mall.MallApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@Slf4j
public class MyCategoryServiceTest extends MallApplicationTests {

    @Autowired
    private MyCategoryService myCategoryService;

    @Test
    public void getCategories() {

    }

    @Test
    public void findSubCategoryId() {
        Set<Integer> set = new HashSet<>();
        myCategoryService.findSubCategoryId(100001, set);
        log.info("set={}", set);
    }
}