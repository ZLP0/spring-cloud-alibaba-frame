package com.user.api.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "user-service")
public interface UserFegin {

    @RequestMapping(value = "providerSay")
    public String say();
}