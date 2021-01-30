package com.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * feign接口
 * (1) 通过FeignClient指向服务的提供者
 * (2) 通过与提供者相同的mapping映射方式指向提供者的方法
 */
@FeignClient("provider") // 指向服务提供者的application.name
public interface ConsumerService {

    @RequestMapping("/providerSay") //通过mapping去映射服务提供者的方法, 所有此处的mapping必须相同
    public String say();// 消费者中的方法名可以不与提供者中的方法名相同

}