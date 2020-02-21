package com.dzkjdx.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CartProductVo {
    private Integer id;

    //购买的数量
    private Integer quantity;

    private Integer categoryId;

    private String productName;

    private String productSubtitle;

    private String productMainImage;

    private BigDecimal productPrice;

    private Integer productStatus;

    //总价
    private BigDecimal productTotalPrice;

    private Integer productStock;

    //商品是否选中
    private boolean productSelected;
}
