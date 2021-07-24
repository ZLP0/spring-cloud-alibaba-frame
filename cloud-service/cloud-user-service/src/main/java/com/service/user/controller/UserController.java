package com.service.user.controller;

import com.common.utils.ApiResponse;
import com.common.utils.JwtToken;
import com.common.utils.MD5;
import com.service.dependency.utils.IPUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 程序员  by dell
 * time  2021-01-30
 **/


@Controller
@RequestMapping(value = "/user")
public class UserController {

    @RequestMapping(value = "providerSay")
    @ResponseBody
    public String say() {
        System.out.println("用户服务 say()");
        return "用户服务 say";
    }

    @RequestMapping(value = "/login")
    @ResponseBody
    public ApiResponse login(@RequestParam(value = "username") String username, @RequestParam(value = "pwd") String pwd,
                             HttpServletRequest request) throws Exception {
        //查询用户
        //  UserInfo userinfo = userInfoService.getById(username);

        //匹配
        if (true) {
            //封装令牌
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("name", "zhangsan");
            //获取IP   【绑定IP 防止令牌被窃取】
            String ip = IPUtils.getIpAddr(request);
            dataMap.put("ip", MD5.md5(ip));
            //'创建令牌
            String token = JwtToken.createToken(dataMap);
            return ApiResponse.success(token);
        }

        return ApiResponse.fail("账号或者密码不对");
    }


}
