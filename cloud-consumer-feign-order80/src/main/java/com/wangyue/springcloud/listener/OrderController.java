package com.wangyue.springcloud.listener;

import com.wangyue.springcloud.entities.CommonResult;
import com.wangyue.springcloud.entities.Payment;
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

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> get(@PathVariable Long id){
        return paymentFeignService.getPaymentById(id);
    }

}
