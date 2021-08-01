package com.gateway.api.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 程序员  by dell
 * time  2021-08-01
 **/

@Configuration
public class GatewayConfiguration {

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewayConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        // Register the block exception handler for Spring Cloud Gateway.
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }


    /***
     * 加载 Api、 规则
     */
    @PostConstruct
    public void doInit() {
        initCustomizedApis();//加载 API
        initGatewayRules();// 加载 规则
    }


    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();
        ApiDefinition api1 = new ApiDefinition("api1")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    //完全匹配
                    // add(new ApiPathPredicateItem().setPattern("/order/list"));
                    //前缀匹配
                    add(new ApiPathPredicateItem().setPattern("/order/**").setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
                }});
        ApiDefinition api2 = new ApiDefinition("user-api")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    add(new ApiPathPredicateItem().setPattern("/user/**").setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
                }});
        definitions.add(api1);
        definitions.add(api2);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }


    /***
     * 限流规则定义
     * 1: 针对 initCustomizedApis() 方法中定义的 API
     * 2: 针对路由yaml 中 routes-id
     */
    public void initGatewayRules() {
        //创建集合存储所有规则
        Set<GatewayFlowRule> rules = new HashSet<GatewayFlowRule>();

        //创建新的规则，并添加到集合中
        rules.add(new GatewayFlowRule("order-api")
                //请求的阈值
                .setCount(6)
                //突发流量额外允许并发数量
                .setBurst(2)
                //限流行为
                //CONTROL_BEHAVIOR_RATE_LIMITER  匀速排队
                //CONTROL_BEHAVIOR_DEFAULT  直接失败
                //.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER)
                //排队时间
                //.setMaxQueueingTimeoutMs(10000)
                //统计时间窗口，单位：秒，默认为1秒
                .setIntervalSec(30));

        //创建新的规则，并添加到集合中
        rules.add(new GatewayFlowRule("user-route")
                //请求的阈值     没秒钟不能超过两个请求
                .setCount(2)
                //统计时间窗口，单位：秒，默认为1秒
                .setIntervalSec(1));

        //手动加载规则配置
        GatewayRuleManager.loadRules(rules);
    }


}

