package com.dzkjdx.mall.controller;

import com.dzkjdx.mall.consts.MallConst;
import com.dzkjdx.mall.form.ShippingAddUpdateForm;
import com.dzkjdx.mall.pojo.User;
import com.dzkjdx.mall.service.impl.MyShippingServiceImpl;
import com.dzkjdx.mall.vo.ResponseVo;
import com.dzkjdx.mall.vo.ShippingAddVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class ShippingsController {
    @Autowired
    private MyShippingServiceImpl shippingService;

    @PostMapping("/shippings")
    public ResponseVo<ShippingAddVo> addShippings(@RequestBody ShippingAddUpdateForm shippingAddUpdateForm,
                                                  HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.add(user.getId(), shippingAddUpdateForm);
    }

    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVo deleteShippings(@PathVariable Integer shippingId){
        return shippingService.delete(shippingId);
    }

    @PutMapping("/shippings/{shippingId}")
    public ResponseVo updateShippings(@PathVariable Integer shippingId,
                                      @RequestBody ShippingAddUpdateForm Form,
                                      HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.update(user.getId(), shippingId, Form);
    }
}
