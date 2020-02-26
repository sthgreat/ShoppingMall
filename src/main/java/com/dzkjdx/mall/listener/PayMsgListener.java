package com.dzkjdx.mall.listener;

import com.dzkjdx.mall.pojo.PayInfo;
import com.dzkjdx.mall.service.IOrderService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
@Slf4j
@RabbitListener(queues = "payNotify")
public class PayMsgListener {
    @Autowired
    private IOrderService orderService;

    @RabbitHandler
    public void process(String msg){
        log.info("接收到消息 -> {}", msg);
        PayInfo payInfo = new Gson().fromJson(msg, PayInfo.class);
        if(payInfo.getPlatformStatus().equals("SUCCESS")){
            //修改订单状态
            orderService.paid(payInfo.getOrderNo());
        }
    }
}
