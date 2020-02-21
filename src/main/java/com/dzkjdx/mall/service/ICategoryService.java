package com.dzkjdx.mall.service;

import com.dzkjdx.mall.pojo.Category;
import com.dzkjdx.mall.vo.CategoryVo;
import com.dzkjdx.mall.vo.ResponseVo;

import java.util.List;
import java.util.Set;

public interface ICategoryService {
    ResponseVo<List<CategoryVo>> getCategories();

    void findSubCategoryId(Integer Id, Set<Integer> ResultSet);
}
