package com.dzkjdx.mall.controller;

import com.dzkjdx.mall.consts.MallConst;
import com.dzkjdx.mall.form.OrderCreateForm;
import com.dzkjdx.mall.pojo.User;
import com.dzkjdx.mall.service.impl.OrderServiceImpl;
import com.dzkjdx.mall.vo.OrderVo;
import com.dzkjdx.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("/orders")
    public ResponseVo<OrderVo> create(@Valid @RequestBody OrderCreateForm form,
                                      HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.create(user.getId(), form.getShippingId());
    }

    @GetMapping("/orders")
    public ResponseVo<PageInfo> list(@RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize,
                                     HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.list(user.getId(), pageNum, pageSize);
    }

    @GetMapping("/orders/{orderNo}")
    public ResponseVo<OrderVo> detail(@PathVariable(value = "orderNo") Long orderNo,
                                      HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.detail(user.getId(), orderNo);
    }

    @PutMapping("/orders/{orderNo}")
    public ResponseVo cancel(@PathVariable(value = "orderNo") Long orderNo,
                             HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.cancel(user.getId(), orderNo);
    }
}
