package com.wangyue.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

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
            TimeUnit.SECONDS.sleep(5);
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
}
