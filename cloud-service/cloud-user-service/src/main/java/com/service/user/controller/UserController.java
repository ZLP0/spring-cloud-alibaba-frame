package com.service.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 程序员  by dell
 * time  2021-01-30
 **/


@Controller
public class UserController {

    @RequestMapping(value = "providerSay")
    @ResponseBody
    public String  say(){
        System.out.println("用户服务 say()");
        return "用户服务 say";
    }
}
