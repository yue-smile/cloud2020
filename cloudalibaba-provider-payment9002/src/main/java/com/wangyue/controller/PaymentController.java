package com.wangyue.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @GetMapping(value = "/nacosTest")
    public String  nacosTest(){
        return "nacos我是9002，这个业务返回结果";
    }
}
