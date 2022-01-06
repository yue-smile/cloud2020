package com.wangyue.cloud.service;

import org.springframework.cloud.stream.annotation.Output;

public interface IMessgaeProvider {
    String OUTPUT = "output1";

    @Output(OUTPUT)
    boolean send();
}
