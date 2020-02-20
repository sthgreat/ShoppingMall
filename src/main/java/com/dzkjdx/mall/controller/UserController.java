package com.dzkjdx.mall.controller;

import com.dzkjdx.mall.consts.MallConst;
import com.dzkjdx.mall.enums.ResponseEnum;
import com.dzkjdx.mall.form.UserRegisterForm;
import com.dzkjdx.mall.form.UserLoginForm;
import com.dzkjdx.mall.pojo.User;
import com.dzkjdx.mall.service.IUserSerivce;
import com.dzkjdx.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private IUserSerivce userSerivce;

    @PostMapping("/user/register")
    public ResponseVo register(@Valid @RequestBody UserRegisterForm userRegisterForm,
                               BindingResult bindingResult){
        log.info("username:"+ userRegisterForm.getUsername());
        if(bindingResult.hasErrors()){
            log.error("注册提交参数有误：{} {}",
                    Objects.requireNonNull(bindingResult.getFieldError()).getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
        }

        User user = new User();
        BeanUtils.copyProperties(userRegisterForm, user);
        return userSerivce.register(user);
    }

    @PostMapping("/user/login")
    public ResponseVo login(@Valid @RequestBody UserLoginForm userLoginForm,
                            BindingResult bindingResult,
                            HttpServletRequest httpServletRequest){
        if(bindingResult.hasErrors()){
            log.error("登陆提交参数有误：{} {}",
                    Objects.requireNonNull(bindingResult.getFieldError()).getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
        }
        ResponseVo<User> userResponseVo = userSerivce.login(userLoginForm.getUsername(), userLoginForm.getPassword());
        //设置session，保存在内存中，也可保存到redis中
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(MallConst.CURRENT_USER, userResponseVo.getData());
        log.info("/login,sessionId={}",session.getId());

        return userResponseVo;
    }

    @GetMapping("/user")
    public ResponseVo<User> userInfo(HttpSession session){
        log.info("/user,sessionId={}",session.getId());
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);

        return ResponseVo.success(user);
    }

    //TODO 判断登录状态
    @PostMapping("/user/logout")
    public ResponseVo logout(HttpSession session){
        log.info("/user/logout,sessionId={}",session.getId());

        session.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVo.success();
    }
}
