package com.wangyue.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.wangyue.springcloud.service.PaymentFallbackService;
import com.wangyue.springcloud.service.PaymentFeignService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class ConsumerFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("feign/get/{id}")
//    @SentinelResource(value = "consumerGet")
//    @SentinelResource(value = "consumerGet" ,fallback = "handlerFallback")//fallback 只负责程序出现异常，程序出现异常exception
//    @SentinelResource(value = "consumerGet" ,blockHandler = "blockHandler")//blockHandler 只负责sentinel控制台配置违规
//    @SentinelResource(value = "consumerGet" ,fallback = "handlerFallback",blockHandler = "blockHandler")//当点击过快，同时系统内会有异常时，会先报blockHandler，不会进fallback
//    @SentinelResource(value = "consumerGet",fallbackClass  = PaymentFallbackService.class,blockHandler = "blockHandler",exceptionsToIgnore = {IllegalArgumentException.class,NullPointerException.class})//exceptionsToIgnore表示忽略某些异常不进fallback
    public String find(@PathVariable("id")Long id){
        if(id==2){
            throw new IllegalArgumentException("非法参数异常");
        }else if(id==3){
            throw new NullPointerException("记录查不到空指针异常");
        }
        return paymentFeignService.getPaymentById(id);
    }

    public String handlerFallback(Long id){
        return "已经入异常fallback，查询id为"+id;
    }

    public String blockHandler(Long id){
        return "已经触发sentinel规则blockHandler，查询id为"+id;
    }
}
