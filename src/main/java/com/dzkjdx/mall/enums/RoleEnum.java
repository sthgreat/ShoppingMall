package com.dzkjdx.mall.enums;

import lombok.Data;
import lombok.Getter;

import javax.management.relation.Role;

/**
 * 角色0-管理员，1-普通用户
 */
@Getter
public enum RoleEnum {
    CUSTOMER(1),

    ADMIN(0),
    ;

    private int code;

    RoleEnum(int code) {
        this.code = code;
    }
}
