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
@RefreshScope// 动态刷新
public class AppController {

    @Value("${animal.name}")
    private String name;

    @Value("${animal.age}")
    private int age;


    @Value("${person.name}")
    private String personName;

    @Value("${person.age}")
    private int personAge;

    @RequestMapping(value = "/config")
    public String config(){
        System.out.println("执行配置");
        return name+":"+age;
    }

    @RequestMapping(value = "/personConfig")
    public String config2(){
        System.out.println("执行配置");
        return personName+":"+personAge;
    }
}
