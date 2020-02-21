package com.dzkjdx.mall.service.impl;

import com.dzkjdx.mall.dao.ProductMapper;
import com.dzkjdx.mall.pojo.Product;
import com.dzkjdx.mall.service.ICategoryService;
import com.dzkjdx.mall.service.IProductService;
import com.dzkjdx.mall.vo.ProductVo;
import com.dzkjdx.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private MyCategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseVo<List<ProductVo>> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet<>();
        if(categoryId != null){
            categoryService.findSubCategoryId(categoryId, categoryIdSet);
            categoryIdSet.add(categoryId);
        }

        List<Product> products = productMapper.selectByCategoryIdSet(categoryIdSet);
        log.info("products={}",products);

        List<ProductVo> productVoList = new ArrayList<>();
        for(Product product : products){
            ProductVo productVo = new ProductVo();
            BeanUtils.copyProperties(product, productVo);
            productVoList.add(productVo);
        }
        return ResponseVo.success(productVoList);
    }
}
