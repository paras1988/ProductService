package com.patti.paymentGateway;


public interface PaymentGateway {
    String generatePaymentLink(String orderId, Long amount);
}
