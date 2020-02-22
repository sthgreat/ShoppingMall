package com.dzkjdx.mall.service.impl;

import com.dzkjdx.mall.dao.ShippingMapper;
import com.dzkjdx.mall.form.ShippingAddUpdateForm;
import com.dzkjdx.mall.pojo.Shipping;
import com.dzkjdx.mall.service.MyShippingService;
import com.dzkjdx.mall.vo.ResponseVo;
import com.dzkjdx.mall.vo.ShippingAddVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyShippingServiceImpl implements MyShippingService {
    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ResponseVo<ShippingAddVo> add(Integer uid, ShippingAddUpdateForm shippingAddUpdateForm) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(shippingAddUpdateForm, shipping);
        shipping.setUserId(uid);
        int insert = shippingMapper.insert(shipping);
        if(insert == 1){
            return ResponseVo.success(new ShippingAddVo(shipping.getId()), "新建地址成功");
        }else{
            return new ResponseVo<>(1, "新建地址失败");
        }
    }

    @Override
    public ResponseVo delete( Integer shippingId) {
        int i = shippingMapper.deleteByPrimaryKey(shippingId);
        if(i == 1){
            return ResponseVo.successByMsg("删除成功");
        }else {
            return new ResponseVo(1,"删除地址失败");
        }
    }

    @Override
    public ResponseVo update(Integer uid,
                             Integer shippingId,
                             ShippingAddUpdateForm shippingAddUpdateForm) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(shippingAddUpdateForm, shipping);
        shipping.setId(shippingId);
        shipping.setUserId(uid);
        int i = shippingMapper.updateByPrimaryKey(shipping);
        if(i == 1){
            return ResponseVo.successByMsg("更新地址成功");
        }else {
            return new ResponseVo(1, "更新地址失败");
        }
    }
}
