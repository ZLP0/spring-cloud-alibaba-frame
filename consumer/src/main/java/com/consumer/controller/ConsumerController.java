package com.consumer.controller;

import com.consumer.service.ConsumerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 程序员  by dell
 * time  2021-01-30
 **/

@Controller
public class ConsumerController {

    @Resource
    private ConsumerService consumerService;


    @ResponseBody
    @RequestMapping(value = "/consumerSay")
    public String say() {
        System.out.println("consumer 执行了");
        return consumerService.say();
    }
}
