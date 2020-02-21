package com.dzkjdx.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CartAddForm {
    //NotEmpty用于判断集合
    //NotBlank用于判断String
    //NotNull
    @NotNull
    private Integer productId;

    private Boolean selected = true;
}
