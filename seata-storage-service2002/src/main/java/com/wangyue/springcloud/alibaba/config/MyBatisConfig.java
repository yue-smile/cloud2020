package com.wangyue.springcloud.alibaba.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan({"com.wangyue.springcloud.alibaba.dao"})
public class MyBatisConfig {

}