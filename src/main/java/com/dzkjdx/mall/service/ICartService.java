package com.dzkjdx.mall.service;

import com.dzkjdx.mall.form.CartAddForm;
import com.dzkjdx.mall.form.CartUpdateForm;
import com.dzkjdx.mall.vo.CartVo;
import com.dzkjdx.mall.vo.ResponseVo;

public interface ICartService {
    ResponseVo<CartVo> add(Integer uid, CartAddForm cartAddForm);

    ResponseVo<CartVo> list(Integer uid);

    ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);

    ResponseVo<CartVo> delete(Integer uid, Integer productId);

    ResponseVo<CartVo> selectAll(Integer uid);

    ResponseVo<CartVo> unSelectAll(Integer uid);

    ResponseVo<Integer> sum(Integer uid);
}
