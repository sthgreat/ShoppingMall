package com.dzkjdx.mall.service.impl;

import com.dzkjdx.mall.dao.ProductMapper;
import com.dzkjdx.mall.enums.ProductEnum;
import com.dzkjdx.mall.enums.ResponseEnum;
import com.dzkjdx.mall.pojo.Product;
import com.dzkjdx.mall.service.ICategoryService;
import com.dzkjdx.mall.service.IProductService;
import com.dzkjdx.mall.vo.ProductDetailVo;
import com.dzkjdx.mall.vo.ProductVo;
import com.dzkjdx.mall.vo.ResponseVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet<>();
        if(categoryId != null){
            categoryService.findSubCategoryId(categoryId, categoryIdSet);
            categoryIdSet.add(categoryId);
        }

        PageHelper.startPage(pageNum,pageSize);
        List<Product> products = productMapper.selectByCategoryIdSet(categoryIdSet);
        log.info("products={}",products);

        List<ProductVo> productVoList = new ArrayList<>();
        for(Product product : products){
            ProductVo productVo = new ProductVo();
            BeanUtils.copyProperties(product, productVo);
            productVoList.add(productVo);
        }
        PageInfo pageInfo = new PageInfo<>(products);
        pageInfo.setList(productVoList);
        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<ProductDetailVo> selectByProductId(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product.getStatus().equals(ProductEnum.OFF_SAIL.getStatus()) ||
                product.getStatus().equals(ProductEnum.DELETE.getStatus())){
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SAIL_OR_DELETE);
        }
        ProductDetailVo productDetailVo = new ProductDetailVo();
        BeanUtils.copyProperties(product, productDetailVo);
        //对敏感数据处理
        productDetailVo.setStock(product.getStock()>100 ? 100:product.getStock());
        return ResponseVo.success(productDetailVo);
    }
}
