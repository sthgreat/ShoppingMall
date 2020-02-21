package com.dzkjdx.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartVo {
    private List<CartProductVo> cartProductVoList;

    private boolean selectAll;

    private BigDecimal cartTotalPrice;

    private Integer cartTotalQuantity;
}
