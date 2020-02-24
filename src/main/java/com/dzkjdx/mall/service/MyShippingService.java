package com.dzkjdx.mall.service;

import com.dzkjdx.mall.form.ShippingAddUpdateForm;
import com.dzkjdx.mall.vo.ResponseVo;
import com.dzkjdx.mall.vo.ShippingAddVo;
import com.github.pagehelper.PageInfo;

public interface MyShippingService {
    ResponseVo<ShippingAddVo> add(Integer uid, ShippingAddUpdateForm shippingAddUpdateForm);

    ResponseVo delete(Integer shippingId);

    ResponseVo update(Integer uid, Integer shippingId, ShippingAddUpdateForm shippingAddUpdateForm);

    ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);
}
