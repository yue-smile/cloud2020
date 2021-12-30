package com.wangyue.springcloud.service;

import com.wangyue.springcloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "cloud-payment-service")
public interface PaymentFeignService {
    @GetMapping(value = "payment/get/{id}")
    CommonResult getPaymentById(@PathVariable("id")Long id);
}
