package com.wangyue.springcloud.listener;

import com.wangyue.springcloud.entities.CommonResult;
import com.wangyue.springcloud.entities.Payment;
import com.wangyue.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Resource
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private  String serverProt;

    @PostMapping(value = "payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        //@RequestBody 对应postman中post请求raw contenttype application/json
        log.info("插入结果");
        if (paymentService.create(payment) >= 1) {
            return new CommonResult(200, "插入成功 端口号为"+ serverProt, null);
        }else{
            return new CommonResult(444, "插入失败22", null);
        }
    }


    @GetMapping(value = "payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id")Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("插入结果");
        if(payment!=null){
            return new CommonResult(200,"查询成功 端口号为"+ serverProt,payment);
        }
        return new CommonResult(444,"没有对应记录",null);
    }

    @GetMapping(value = "payment/discovery")
    public CommonResult discovery(){
        List<String> services =  discoveryClient.getServices();
        for (String record:services) {
            log.info(">>>>>>>>>>"+record);
        }
        List<ServiceInstance> instances  = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance:instances) {
            log.info(">>>>>>>>>>"+instance.getHost()+">>>>>>>>>>"+instance.getInstanceId()+">>>>>>>>>>"+instance.getPort()+">>>>>>>>>>"+instance.getUri());
        }
        return new CommonResult(200,"查询成功 端口号为"+ serverProt,services);

    }

}
