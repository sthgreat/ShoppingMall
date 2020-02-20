package com.dzkjdx.mall.controller;

import com.dzkjdx.mall.pojo.Category;
import com.dzkjdx.mall.service.impl.MyCategoryService;
import com.dzkjdx.mall.vo.CategoryVo;
import com.dzkjdx.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyCategoryController {
    @Autowired
    private MyCategoryService myCategoryService;

    @GetMapping("/categories")
    public ResponseVo<List<CategoryVo>> getCategories(){
        return myCategoryService.getCategories();
    }
}
