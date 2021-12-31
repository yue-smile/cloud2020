package com.wangyue.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentFeignService {
    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "我是80，服务已经降级啦---------PaymentFallbackService-paymentInfo_Timeout fall back";
    }

    @Override
    public String paymentInfo_OK(Integer id) {
        return "我是80，服务已经降级啦---------PaymentFallbackService-paymentInfo_OK fall back";
    }
}
