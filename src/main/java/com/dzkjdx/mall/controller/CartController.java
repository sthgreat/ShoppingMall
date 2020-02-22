package com.dzkjdx.mall.controller;

import com.dzkjdx.mall.consts.MallConst;
import com.dzkjdx.mall.form.CartAddForm;
import com.dzkjdx.mall.form.CartUpdateForm;
import com.dzkjdx.mall.pojo.User;
import com.dzkjdx.mall.service.impl.CartService;
import com.dzkjdx.mall.vo.CartProductVo;
import com.dzkjdx.mall.vo.CartVo;
import com.dzkjdx.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import javax.xml.ws.Response;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/carts")
    public ResponseVo<CartVo> list(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.list(user.getId());
    }

    @PostMapping("/carts")
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm,
                                  HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);;
        return cartService.add(user.getId() ,cartAddForm);
    }

    @PutMapping("/carts/{productId}")
    public ResponseVo<CartVo> update(@PathVariable(value = "productId") Integer productId,
                                     HttpSession session,
                                     @Valid @RequestBody CartUpdateForm cartUpdateForm){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);;
        return cartService.update(user.getId(), productId, cartUpdateForm);
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseVo<CartVo> delete(@PathVariable(value = "productId") Integer productId,
                                     HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);;
        return cartService.delete(user.getId(), productId);
    }

    @PutMapping("/carts/selectAll")
    public ResponseVo selectAll(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);;
        return cartService.selectAll(user.getId());
    }

    @PutMapping("/carts/unSelectAll")
    public ResponseVo unSelectAll(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);;
        return  cartService.unSelectAll(user.getId());
    }

    @GetMapping("/carts/products/sum")
    public ResponseVo sum(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);;
        return  cartService.sum(user.getId());
    }
}
