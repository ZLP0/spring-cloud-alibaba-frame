package com.gateway.api.permission;


import com.common.utils.JwtToken;
import com.common.utils.MD5;
import com.gateway.api.utils.IpUtil;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.Map;

@Component
public class AuthorizationIntterceptor {


    /***
     * 令牌校验
     */
    public Map<String, Object> tokenIntercept(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        //校验其他地址
        String clientIp = IpUtil.getIp(request);
        //获取令牌
        String token = request.getHeaders().getFirst("authorization");
        //令牌校验
        Map<String, Object> resultMap = AuthorizationIntterceptor.jwtVerify(token, clientIp);
        return resultMap;
    }


    /*****
     * 令牌解析
     */
    public static Map<String, Object> jwtVerify(String token, String clientIp) {
        try {
            //解析Token
            Map<String, Object> dataMap = JwtToken.parseToken(token);
            //获取Token中IP的MD5
            String ip = dataMap.get("ip").toString();
            //比较Token中IP的MD5值和用户的IPMD5值
            if (ip.equals(MD5.md5(clientIp))) {
                return dataMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
