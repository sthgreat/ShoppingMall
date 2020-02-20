package com.dzkjdx.mall.service.impl;

import com.dzkjdx.mall.MallApplicationTests;
import com.dzkjdx.mall.enums.ResponseEnum;
import com.dzkjdx.mall.enums.RoleEnum;
import com.dzkjdx.mall.pojo.User;
import com.dzkjdx.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
public class UserSerivceImplTest extends MallApplicationTests {

    @Autowired
    private UserSerivceImpl userSerivce;

    @Before
    public void register() {
        User user = new User("jsb","123456","284@qq.com", RoleEnum.CUSTOMER.getCode());
        userSerivce.register(user);
    }

    @Test
    public void login(){
        ResponseVo<User> responseVo = userSerivce.login("jsb", "123");
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}