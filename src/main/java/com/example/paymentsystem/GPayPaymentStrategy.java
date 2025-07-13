package com.example.paymentsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("gpay")
public class GPayPaymentStrategy implements PaymentStrategy {

    private static final Logger logger = LoggerFactory.getLogger(GPayPaymentStrategy.class);

    @Override
    public void pay(PaymentDetails paymentDetails) {
        logger.info("Processing GPay payment for processing ID: {}", paymentDetails.getProcessingId());
        // In a real application, you would integrate with the GPay API here
        paymentDetails.setStatus(PaymentStatus.SUCCESSFUL);
        logger.info("GPay payment successful for processing ID: {}", paymentDetails.getProcessingId());
    }
}
