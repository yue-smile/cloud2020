package com.wangyue.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class FlowLimitController {

    @GetMapping(value="/test1")
    public String test1(){
        return "test1-----hello-----";
    }

    @GetMapping(value="/test2")
    public String test2(){
        return "test2-----hello-----";
    }

    @GetMapping(value="/test3")
    public String test3(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "test3-----hello-----";
    }

    @GetMapping(value="/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1",required = false)String p1,
                             @RequestParam(value = "p2",required = false)String p2){

        return "testHotKey-----hello-----";
    }

    public String deal_testHotKey(@RequestParam(value = "p1",required = false)String p1,
                                  @RequestParam(value = "p2",required = false)String p2,
                                  BlockException e){
        return "deal_testHotKey-----hello-----";
    }



}
