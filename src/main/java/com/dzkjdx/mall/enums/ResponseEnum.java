package com.dzkjdx.mall.enums;

import lombok.Getter;

@Getter
public enum  ResponseEnum {
    SUCCESS(0,"成功"),

    PASSWORD_ERROR(1,"密码错误"),

    USERNAME_EXIST(2,"用户名已存在"),

    NEED_LOGIN(10,"用户未登录，请先登录"),

    PARAM_ERROR(3,"参数错误"),

    EMAIL_EXIST(4,"邮箱已存在"),

    ERROR(-1,"服务端错误"),

    USERNAME_OR_PASSWORD_ERROR(11, "用户名或者密码错误"),

    PRODUCT_OFF_SAIL_OR_DELETE(12,"商品下架或者删除"),

    PRODUCT_NOT_EXIST(13, "商品不存在"),

    STOCK_NOT_ENOUGH(14, "库存不足"),

    CART_PRODUCT_NOT_EXSIST(15, "购物车商品不存在"),

    SHIPPING_NOT_EXIST(17, "收货地址不存在"),

    SELECT_LIST_IS_EMPTY(18, "请选择商品后下单"),

    ORDER_NOT_EXIST(19, "订单不存在"),

    ORDER_STATUS_ERROR(20, "订单状态有误"),

    ;
    Integer code;

    String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
