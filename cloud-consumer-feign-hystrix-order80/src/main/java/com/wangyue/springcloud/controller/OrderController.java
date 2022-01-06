package com.wangyue.springcloud.controller;

import com.wangyue.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
//@DefaultProperties(defaultFallback = "global_PaymentInfo_TimeoutHandler")
public class OrderController {

    @Resource
    private PaymentFeignService paymentFeignService;

//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
//    })
    @GetMapping(value = "/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable(value = "id") Integer id){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return paymentFeignService.paymentInfo_OK(id);
    }

    @GetMapping(value = "/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler" ,commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
//    })
    public String paymentInfo_Timeout(@PathVariable(value = "id") Integer id){
        return paymentFeignService.paymentInfo_Timeout(id);
    }

//    public String paymentInfo_TimeoutHandler(@PathVariable(value = "id") Integer id){
//        return "我是消费者80服务已降级，请稍后再试！！！需要查询的id为：" + id;
//    }
//
//    public String global_PaymentInfo_TimeoutHandler(){
//        return "我是消费者80全局的默认fallback，请稍后请稍后";
//    }
}
