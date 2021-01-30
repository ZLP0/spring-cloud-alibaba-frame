package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 程序员  by dell
 * time  2021-01-30
 **/

@RestController
@RefreshScope
public class AppController {

    @Value("${animal.name}")
    private String name;


    @RequestMapping(value = "/config")
    public String config(){

        System.out.println("执行配置");
        return name;

    }
}
