package com.wangyue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PaymentNacosMain9002 {
     public static void main(String[] args) {
           SpringApplication.run(PaymentNacosMain9002.class, args);
      }
}
