package com.dzkjdx.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginForm {
    @NotBlank(message = "用户名不能为空") //用于string，判断空格
    //@NotEmpty 用于集合
    //NotNull 是否为null
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
