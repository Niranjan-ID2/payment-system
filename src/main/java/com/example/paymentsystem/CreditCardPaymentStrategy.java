package com.example.paymentsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("creditCard")
public class CreditCardPaymentStrategy implements PaymentStrategy {

    private static final Logger logger = LoggerFactory.getLogger(CreditCardPaymentStrategy.class);

    @Override
    public void pay(PaymentDetails paymentDetails) {
        logger.info("Processing Credit Card payment for processing ID: {}", paymentDetails.getProcessingId());
        // In a real application, you would integrate with a payment gateway here
        paymentDetails.setStatus(PaymentStatus.SUCCESSFUL);
        logger.info("Credit Card payment successful for processing ID: {}", paymentDetails.getProcessingId());
    }
}
