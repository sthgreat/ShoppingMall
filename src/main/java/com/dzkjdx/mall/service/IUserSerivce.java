package com.dzkjdx.mall.service;

import com.dzkjdx.mall.pojo.User;
import com.dzkjdx.mall.vo.ResponseVo;

public interface IUserSerivce {
    /**
     * 注册
     */
    ResponseVo register(User user);

    /**
     * 登陆
     * @param
     */
    ResponseVo<User> login(String username,String password);

}
