package com.dzkjdx.mall.interceptor;

import com.dzkjdx.mall.consts.MallConst;
import com.dzkjdx.mall.exception.UserLoginException;
import com.dzkjdx.mall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {
    @Override
    /**
     * true表示继续流程，false表示中断
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("prehandle...");
        User user = (User) request.getSession().getAttribute(MallConst.CURRENT_USER);
        if(user == null){
//            return ResponseVo.error(ResponseEnum.NEED_LOGIN);
            log.info("user==null");
            throw new UserLoginException();
        }
        return true;
    }
}
