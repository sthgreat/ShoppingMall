package com.dzkjdx.mall.service;

import com.dzkjdx.mall.pojo.User;

public interface IUserSerivce {
    /**
     * 注册
     */
    void register(User user);

    /**
     * 登陆
     * @param user
     */
    void login(User user);

}
