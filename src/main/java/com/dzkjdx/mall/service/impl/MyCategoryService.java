package com.dzkjdx.mall.service.impl;

import com.dzkjdx.mall.consts.MallConst;
import com.dzkjdx.mall.dao.CategoryMapper;
import com.dzkjdx.mall.pojo.Category;
import com.dzkjdx.mall.service.ICategoryService;
import com.dzkjdx.mall.vo.CategoryVo;
import com.dzkjdx.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
public class MyCategoryService implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVo<List<CategoryVo>> getCategories() {
        List<Category> categories = categoryMapper.selectCategories();
        ArrayList<CategoryVo> resultVo = new ArrayList<>();
        for(Category category : categories){
            if(category.getParentId().equals(MallConst.ROOT_PARENT_ID)){
                CategoryVo categoryVo = new CategoryVo();
                BeanUtils.copyProperties(category, categoryVo); //先转换成vo对象
                resultVo.add(categoryVo); //添加父类到第一层
            }
        }
        resultVo.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());//排序
        findSubCategory(categories ,resultVo);

        return ResponseVo.success(resultVo);
    }

    @Override
    public void findSubCategoryId(Integer id, Set<Integer> resultSet) {
        List<Category> categories = categoryMapper.selectCategories();
        findSubCategoryId(categories, id, resultSet);
    }

    private void findSubCategoryId(List<Category> categories, Integer id, Set<Integer> resultSet){
        for(Category category : categories){
            if(category.getParentId().equals(id)){
                resultSet.add(category.getId());

                findSubCategoryId(categories, category.getId(),resultSet);
            }
        }
    }

    private void findSubCategory(List<Category> categoryList, ArrayList<CategoryVo> resultVo){
        if(resultVo.isEmpty()){
            return;
        }

        for(CategoryVo categoryVo : resultVo){
            ArrayList<CategoryVo> subCategoryList = new ArrayList<>();
            for(Category category : categoryList){
                //查到内容，设置，继续向下查
                if(category.getParentId().equals(categoryVo.getId())){
                    CategoryVo subCategoryVo = new CategoryVo();
                    BeanUtils.copyProperties(category, subCategoryVo);
                    subCategoryList.add(subCategoryVo);
                }
            }
            subCategoryList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
            categoryVo.setSubCategories(subCategoryList);

            findSubCategory(categoryList, subCategoryList);
        }
    }
}
