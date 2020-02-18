package com.dzkjdx.mall.service.impl;

import com.dzkjdx.mall.dao.UserMapper;
import com.dzkjdx.mall.pojo.User;
import com.dzkjdx.mall.service.IUserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Service
public class UserSerivceImpl implements IUserSerivce {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) {
        //校验 用户名，邮箱不能重复
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if(countByUsername>0){
            throw new RuntimeException("该用户名已经注册");
        }
        int countByEmail = userMapper.countByUsername(user.getEmail());
        if(countByEmail>0){
            throw new RuntimeException("该邮箱已经注册");
        }
        //md5加密（Spring自带）
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));

        //写入数据库
        int resultConut = userMapper.insertSelective(user);
        if(resultConut==0){
            throw new RuntimeException("注册失败");
        }
    }

    @Override
    public void login(User user) {

    }
}
