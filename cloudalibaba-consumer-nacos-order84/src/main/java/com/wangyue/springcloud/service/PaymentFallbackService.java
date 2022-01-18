package com.wangyue.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentFeignService {
    @Override
    public String getPaymentById(Long id) {
        return "我是84，服务已经降级啦---------PaymentFallbackService-paymentInfo_Timeout fall back";
    }

}
