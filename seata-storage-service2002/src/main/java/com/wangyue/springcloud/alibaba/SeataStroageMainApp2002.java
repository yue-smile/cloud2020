package com.wangyue.springcloud.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication//(exclude = DataSourceAutoConfiguration.class)//取消数据源自动创建的配置
public class SeataStroageMainApp2002 {

    public static void main(String[] args)
    {
        SpringApplication.run(SeataStroageMainApp2002.class, args);
    }
}
