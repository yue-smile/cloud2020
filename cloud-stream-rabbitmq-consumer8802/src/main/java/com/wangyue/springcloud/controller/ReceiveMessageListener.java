package com.wangyue.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(IReceiveMessageListener.class)
public class ReceiveMessageListener {
    @Value("${server.port}")
    private String port;

    @StreamListener(IReceiveMessageListener.INPUT)
    public void onReceiver(Message<String> message){
        System.out.println("消费者"+port+"接受到的消息为：" + message.getPayload());
    }

}
