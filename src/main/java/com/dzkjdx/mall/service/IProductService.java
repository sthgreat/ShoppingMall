package com.dzkjdx.mall.service;

import com.dzkjdx.mall.vo.ProductVo;
import com.dzkjdx.mall.vo.ResponseVo;

import java.util.List;

public interface IProductService {
    ResponseVo<List<ProductVo>> list(Integer categoryId, Integer pageNum, Integer pageSize);
}
