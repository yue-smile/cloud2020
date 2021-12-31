package com.wangyue.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String paymentInfo_OK(Integer id){
        String result = "线程池： "+ Thread.currentThread().getName()+ "paymentInfo_OK:id "+ id + "成功";
        System.out.println(result);
        return result;
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfo_Timeout(Integer id){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String result = "线程池： "+ Thread.currentThread().getName()+ "paymentInfo_Timeout:id "+ id + "超时";
        System.out.println(result);
        return  result;
    }

    public String paymentInfo_TimeoutHandler(Integer id){
        String result = "线程池： "+ Thread.currentThread().getName()+ "paymentInfo_TimeoutHandler:id "+ id + "服务已降级";
        System.out.println(result);
        return  result;
    }


    //-----服务熔断  所有属性在HystrixPropertiesManager中
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),//失败率达到多少跳闸 10000毫秒内，10次请求，失败60%即6次 就跳闸
    })
    public String paymentCircuitBreaker(@PathVariable(value = "id")Integer id){
        if(id<0){
            throw new RuntimeException("id不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return  Thread.currentThread().getName()+"调用成功！！！流水号为： "+ serialNumber;

    }

    public String paymentCircuitBreaker_fallback(@PathVariable(value = "id")Integer id){
        return  "服务已降级---id不能为负数 请稍后重试，id为："+ id;
    }

}
