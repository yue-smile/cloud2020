package com.wangyue.springcloud.service;

import com.wangyue.springcloud.entities.Payment;

public interface PaymentService {
    int create(Payment payment);
    Payment getPaymentById(Long id);
}
