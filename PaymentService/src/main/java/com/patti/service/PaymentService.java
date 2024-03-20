package com.patti.service;

import com.patti.dto.InitiatePaymentDto;
import com.patti.paymentGateway.PaymentGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    PaymentGateway paymentGateway;
    public String initiatePayment(InitiatePaymentDto initiatePaymentDto){
        //Order order = OrderService.getOrderDetails(initiatePaymentDto.getOrderId());

        Long amount = 1000L;
        String ss = paymentGateway.generatePaymentLink(initiatePaymentDto.getOrderId(),amount);
        return ss;
    }

}
