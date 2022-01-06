package com.wangyue.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Value("${serverurl.provider-service}")
    private String providerName;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/get")
    public String get(){
        ResponseEntity<String> result = restTemplate.getForEntity(providerName+"/nacosTest",String.class);
        return result.getBody();
    }
}
