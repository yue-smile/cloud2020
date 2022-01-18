package com.wangyue.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AlibabaMain9004 {
 public static void main(String[] args) {
       SpringApplication.run(AlibabaMain9004.class, args);
  }

}
