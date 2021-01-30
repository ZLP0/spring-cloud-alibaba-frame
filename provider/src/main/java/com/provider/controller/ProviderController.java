package com.provider.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 程序员  by dell
 * time  2021-01-30
 **/


@Controller
public class ProviderController {

    @RequestMapping(value = "providerSay")
    @ResponseBody
    public String  say(){
        System.out.println("提供者执行了");
        return "你好我是提供者";
    }
}
