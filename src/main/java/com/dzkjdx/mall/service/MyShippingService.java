package com.dzkjdx.mall.service;

import com.dzkjdx.mall.form.ShippingAddUpdateForm;
import com.dzkjdx.mall.vo.ResponseVo;
import com.dzkjdx.mall.vo.ShippingAddVo;

public interface MyShippingService {
    ResponseVo<ShippingAddVo> add(Integer uid, ShippingAddUpdateForm shippingAddUpdateForm);

    ResponseVo delete(Integer shippingId);

    ResponseVo update(Integer uid, Integer shippingId, ShippingAddUpdateForm shippingAddUpdateForm);
}
