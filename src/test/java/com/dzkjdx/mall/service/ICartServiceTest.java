package com.dzkjdx.mall.service;

import com.dzkjdx.mall.MallApplicationTests;
import com.dzkjdx.mall.form.CartAddForm;
import com.dzkjdx.mall.form.CartUpdateForm;
import com.dzkjdx.mall.pojo.Cart;
import com.dzkjdx.mall.service.impl.CartService;
import com.dzkjdx.mall.vo.CartVo;
import com.dzkjdx.mall.vo.ResponseVo;
import com.github.pagehelper.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import static org.junit.Assert.*;
@Slf4j
public class ICartServiceTest extends MallApplicationTests {
    @Autowired
    private CartService cartService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void add() {
        CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setProductId(27);
        ResponseVo<CartVo> responseVo = cartService.add(1, cartAddForm);
        log.info("cart:{}", gson.toJson(responseVo));
    }

    @Test
    public void list(){
        ResponseVo<CartVo> list = cartService.list(1);
        log.info("list={}",gson.toJson(list));
    }

    @Test
    public void update(){
        CartUpdateForm cartUpdateForm = new CartUpdateForm();
        cartUpdateForm.setQuantity(5);
        cartUpdateForm.setSelected(false);

        ResponseVo<CartVo> responseVo = cartService.update(1, 26, cartUpdateForm);

        log.info("list={}",gson.toJson(responseVo));

    }

    @Test
    public void delete(){
        ResponseVo<CartVo> responseVo = cartService.delete(1,26);
        log.info("list={}", gson.toJson(responseVo));
    }
}