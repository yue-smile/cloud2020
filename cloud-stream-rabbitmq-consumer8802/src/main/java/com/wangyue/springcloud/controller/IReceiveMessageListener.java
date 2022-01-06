package com.wangyue.springcloud.controller;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface IReceiveMessageListener {
    String INPUT = "input1";

    @Input(INPUT)
    SubscribableChannel receiver();
}
