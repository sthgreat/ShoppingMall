package com.dzkjdx.mall.controller;

import com.dzkjdx.mall.service.impl.ProductServiceImpl;
import com.dzkjdx.mall.vo.ProductDetailVo;
import com.dzkjdx.mall.vo.ProductVo;
import com.dzkjdx.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/products") //返回商品列表（引入pagehelper达到分页效果），参数通过get请求中携带的参数传递
    public ResponseVo<PageInfo> list(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                                     @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize){
        return productService.list(categoryId, pageNum, pageSize);
    }

    @GetMapping("/products/{productId}") //查询某一商品，参数在url中传递
    public ResponseVo<ProductDetailVo> showProductDetail(@PathVariable(value = "productId") Integer productId){
        return productService.selectByProductId(productId);
    }
}
