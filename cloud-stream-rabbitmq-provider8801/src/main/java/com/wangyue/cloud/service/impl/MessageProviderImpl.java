package com.wangyue.cloud.service.impl;

import com.wangyue.cloud.service.IMessgaeProvider;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import javax.annotation.Resource;
import java.util.UUID;


@EnableBinding(Source.class)  //定义消息的推送管道
public class MessageProviderImpl implements IMessgaeProvider {

    @Resource
    private MessageChannel output;

    @Override
    public boolean send() {
        String id  = UUID.randomUUID().toString();
        System.out.println("****************流水号"+id);
        return output.send(MessageBuilder.withPayload(id).build());
    }
}
