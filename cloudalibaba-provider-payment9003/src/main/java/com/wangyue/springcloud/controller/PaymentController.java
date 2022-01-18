package com.wangyue.springcloud.controller;

import com.wangyue.springcloud.entities.CommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Value("${server.port}")
    private String port;

    @GetMapping("payment/get/{id}")
    public CommonResult find(@PathVariable("id")Long id){
        return new CommonResult(200,"已查找到id为"+id+"  from "+port) ;
    }
}
