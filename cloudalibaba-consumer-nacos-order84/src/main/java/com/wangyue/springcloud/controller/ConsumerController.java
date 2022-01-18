package com.wangyue.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class ConsumerController {

    @Resource
    private  RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String serviceUrl;

    @GetMapping("consumer/get/{id}")
//    @SentinelResource(value = "consumerGet")
//    @SentinelResource(value = "consumerGet" ,fallback = "handlerFallback")//fallback 只负责程序出现异常，程序出现异常exception
//    @SentinelResource(value = "consumerGet" ,blockHandler = "blockHandler")//blockHandler 只负责sentinel控制台配置违规
//    @SentinelResource(value = "consumerGet" ,fallback = "handlerFallback",blockHandler = "blockHandler")//当点击过快，同时系统内会有异常时，会先报blockHandler，不会进fallback
    @SentinelResource(value = "consumerGet",fallback = "handlerFallback",blockHandler = "blockHandler",exceptionsToIgnore = {IllegalArgumentException.class,NullPointerException.class})//exceptionsToIgnore表示忽略某些异常不进fallback
    public String find(@PathVariable("id")Long id){
        if(id==2){
            throw new IllegalArgumentException("非法参数异常");
        }else if(id==3){
            throw new NullPointerException("记录查不到空指针异常");
        }
        ResponseEntity<String> result = restTemplate.getForEntity(serviceUrl+"/payment/get/"+id,String.class);
        return result.getBody();
    }

    public String handlerFallback(Long id){
        return "已经入异常fallback，查询id为"+id;
    }

    public String blockHandler(Long id){
        return "已经触发sentinel规则blockHandler，查询id为"+id;
    }
}
