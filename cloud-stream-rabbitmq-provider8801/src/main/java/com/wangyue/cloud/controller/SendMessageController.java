package com.wangyue.cloud.controller;

import com.wangyue.cloud.service.IMessgaeProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SendMessageController {

    @Resource
    private IMessgaeProvider messgaeProvider;

    @GetMapping(value = "/send")
    public boolean sendMsg(){
        return messgaeProvider.send();
    }
}
