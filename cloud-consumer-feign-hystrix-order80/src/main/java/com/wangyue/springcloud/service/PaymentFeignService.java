package com.wangyue.springcloud.service;

import com.wangyue.springcloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "cloud-payment-service")
public interface PaymentFeignService {
    @GetMapping(value = "/payment/hystrix/timeout/{id}")
    String paymentInfo_Timeout(@PathVariable(value = "id") Integer id);

    @GetMapping(value = "/payment/hystrix/ok/{id}")
    String paymentInfo_OK(@PathVariable(value = "id") Integer id);
}
