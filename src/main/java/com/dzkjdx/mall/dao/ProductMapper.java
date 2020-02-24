package com.dzkjdx.mall.dao;

import com.dzkjdx.mall.pojo.Category;
import com.dzkjdx.mall.pojo.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectByCategoryIdSet(@Param("categoryIdSet") Set<Integer> categoryIdSet);

    Product selectByCategoryId(Integer categoryId);

    List<Product> selectByProductIdSet(@Param("productIdSet") Set<Integer> productIdSet);
}