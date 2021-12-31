package com.wangyue.springcloud.controller;

import com.wangyue.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "/consumer/payment/hystrix/timeout/{id}")
    public String paymentInfo_Timeout(@PathVariable(value = "id") Integer id){
        return paymentFeignService.paymentInfo_Timeout(id);
    }

    @GetMapping(value = "/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable(value = "id") Integer id){
        return paymentFeignService.paymentInfo_OK(id);
    }

}
