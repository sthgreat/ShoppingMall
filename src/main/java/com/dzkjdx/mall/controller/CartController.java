package com.dzkjdx.mall.controller;

import com.dzkjdx.mall.form.CartAddForm;
import com.dzkjdx.mall.vo.CartProductVo;
import com.dzkjdx.mall.vo.CartVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.xml.ws.Response;

@Controller
public class CartController {
    @PostMapping("/carts")
    public Response<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm){
        return null;
    }
}
