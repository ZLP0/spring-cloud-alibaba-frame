package com.user.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "user-service")
public interface UserFeign {

    @RequestMapping(value = "/user/providerSay")
    public String say();
}