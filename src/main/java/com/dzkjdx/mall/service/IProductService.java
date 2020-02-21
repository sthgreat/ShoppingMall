package com.dzkjdx.mall.service;

import com.dzkjdx.mall.vo.ProductDetailVo;
import com.dzkjdx.mall.vo.ProductVo;
import com.dzkjdx.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IProductService {
    ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

    ResponseVo<ProductDetailVo> selectByProductId(Integer productId);
}
