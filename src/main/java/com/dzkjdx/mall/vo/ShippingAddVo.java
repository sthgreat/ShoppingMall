package com.dzkjdx.mall.vo;

import lombok.Data;
import org.springframework.web.bind.annotation.DeleteMapping;

@Data
public class ShippingAddVo {
    private Integer shippingId;

    public ShippingAddVo(Integer shippingId) {
        this.shippingId = shippingId;
    }
}
