package com.gateway.api.filter;

import com.alibaba.fastjson.JSON;
import com.common.utils.ApiResponse;
import com.gateway.api.permission.AuthorizationIntterceptor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ApiFilter implements GlobalFilter {

    @Resource
    private AuthorizationIntterceptor authorizationIntterceptor;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        //uri
        String uri = request.getURI().getPath();
        // 放行登录地址 /gateway/user/login
        if (uri.equals("/user/login")) {
            return chain.filter(exchange);
        }
        //令牌校验
        Map<String, Object> resultMap = authorizationIntterceptor.tokenIntercept(exchange);

        if (resultMap == null) {
            //令牌校验失败 或者没有权限
            return endProcess(exchange, 401, "Access denied");
        }


        return chain.filter(exchange);
    }




    public Mono<Void> endProcess(ServerWebExchange exchange, Integer code, String message) {

        ApiResponse<Object> resultMap = new ApiResponse<>();
        resultMap.setCode(code);
        resultMap.setMessage(message);

        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add("message", JSON.toJSONString(resultMap));
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

        DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap(JSON.toJSONBytes(resultMap));
        return exchange.getResponse().writeWith(Flux.just(dataBuffer));

    }
}
