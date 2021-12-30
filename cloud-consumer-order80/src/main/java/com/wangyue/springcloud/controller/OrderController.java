package com.wangyue.springcloud.controller;

import com.wangyue.springcloud.entities.CommonResult;
import com.wangyue.springcloud.entities.Payment;
import com.wangyue.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    //原始httpClient 单机
    //public static final String PAYMENT_URL = "http://127.0.0.1:8001";

    //集群  服务名称
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";


    /*      restTemplate
          提供了多种便捷访问远程http服务的方法
          是一种简单便捷的访问restful服务模板类，是spring提供的用于访问rest服务的客户端模板工具类*/
    @Resource
    private  RestTemplate restTemplate;

    @Resource
    private LoadBalancer loadBalancer;
    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> get(@PathVariable Long id){
//        ResponseEntity<CommonResult> responseEntity = restTemplate.getForEntity(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
//        int resp = responseEntity.getStatusCodeValue();
//        System.out.println(resp);
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

    @GetMapping(value = "/consumer/payment/getlb/{id}")
    public CommonResult<Payment> getPaymentLB(@PathVariable Long id){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(instances.size()>0){
            ServiceInstance serviceInstance = loadBalancer.instances(instances);
            URI uri = serviceInstance.getUri();
            return restTemplate.getForObject(uri+"/payment/get/"+id,CommonResult.class);
        }else{
            CommonResult result = new CommonResult();
            result.setCode(444);
            result.setMessage("无可用服务");
            result.setData(null);
            return result;
        }
    }

}
