package com.service.order.controller;

import com.user.api.fegin.UserFegin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Resource
    private UserFegin userFegin;

    @GetMapping(value = "/selectOrder")
    public String selectOrder(){

        String say = userFegin.say();
        System.out.println(say);
        return "我是一笔订单";
    }
}
