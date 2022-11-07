package com.wsw.concurrent.controller;

import com.wsw.concurrent.rabbit.RabbitProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: wangsongwen
 * @Date: 2022/11/7 14:48
 */
@RestController
public class SendMsgController {
    @Autowired
    private RabbitProvider rabbitProvider;

    @PostMapping("/sendMsg")
    public void sendMsg(@RequestParam String exchangeName, @RequestParam String routingKey) {
        for (int i = 0; i < 100; i++) {
            rabbitProvider.sendMessage(exchangeName, routingKey, i + "a");
        }
    }
}
