package com.dzkjdx.mall.exception;

import com.dzkjdx.mall.enums.ResponseEnum;
import com.dzkjdx.mall.vo.ResponseVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseVo handle(RuntimeException e){//捕获到异常后传入进来
        return ResponseVo.error(ResponseEnum.ERROR, e.getMessage());
    }

    @ExceptionHandler(UserLoginException.class)
    public ResponseVo UserLoginHandle(){
        return ResponseVo.error(ResponseEnum.NEED_LOGIN);
    }
}
