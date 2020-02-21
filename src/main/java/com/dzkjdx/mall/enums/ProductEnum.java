package com.dzkjdx.mall.enums;

import lombok.Getter;

@Getter
public enum ProductEnum {
    ON_SAIL(1),

    OFF_SAIL(2),

    DELETE(3),

    ;

    private Integer status;

    ProductEnum(Integer status){
        this.status = status;
    }
}
