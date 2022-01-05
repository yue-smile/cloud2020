package com.wangyue.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope //修改配置文件后自动刷新  yml配置暴露监控端口management  文件修改后还需要一次post请求刷新  curl -X POST "http://localhost:3355/actuator/refresh"
public class ConfigClientController {
    @Value("${config.info}")
    private String configInfo;

    @Value("${server.port}")
    private String port;

    @GetMapping(value = "/getInfo")
    public String getInfo(){
        return "info>>>>>>>>>"+configInfo+"port>>>>>>>>>>>>>>>"+port;
    }
}
