package com.wangyue.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayConfig {

    //gateway第二种配置路由的方式：硬编码，需要一个RouteLocator  bean
    @Bean
    public RouteLocator routes(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("guonei",
                r -> r.path("/guonei")
                        .uri("http://news.baidu.com/guonei"));
        routes.route("guoji",
                r -> r.path("/guoji")
                        .uri("http://news.baidu.com/guoji"));
        routes.route("mil",
                r -> r.path("/mil")
                        .uri("http://news.baidu.com/mil"));
        routes.route("finance",
                r -> r.path("/finance")
                        .uri("http://news.baidu.com/finance"));
        return routes.build();
    }
}
