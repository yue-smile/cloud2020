package com.wangyue.springcloud.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String paymentInfo_OK(Integer id){
        return "线程池： "+ Thread.currentThread().getName()+ "paymentInfo_OK:id "+ id + "成功";
    }

    public String paymentInfo_Timeout(Integer id){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池： "+ Thread.currentThread().getName()+ "paymentInfo_Timeout:id "+ id + "超时" ;
    }
}
