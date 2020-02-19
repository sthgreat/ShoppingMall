package com.dzkjdx.mall.controller;

import com.dzkjdx.mall.enums.ResponseEnum;
import com.dzkjdx.mall.form.UserForm;
import com.dzkjdx.mall.pojo.User;
import com.dzkjdx.mall.service.IUserSerivce;
import com.dzkjdx.mall.vo.ResponseVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    @Autowired
    private IUserSerivce userSerivce;

    @PostMapping("/register")
    public ResponseVo register(@Valid @RequestBody UserForm userForm,
                               BindingResult bindingResult){
        log.info("username:"+userForm.getUsername());
        if(bindingResult.hasErrors()){
            log.error("注册提交参数有误：{} {}",
                    Objects.requireNonNull(bindingResult.getFieldError()).getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
        }

        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        return userSerivce.register(user);
    }

}
