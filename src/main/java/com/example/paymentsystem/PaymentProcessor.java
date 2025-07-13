package com.example.paymentsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentProcessor {

    private static final Logger logger = LoggerFactory.getLogger(PaymentProcessor.class);

    private final Map<String, PaymentStrategy> paymentStrategies;

    public PaymentProcessor(Map<String, PaymentStrategy> paymentStrategies) {
        this.paymentStrategies = paymentStrategies;
    }

    public void processPayment(PaymentDetails paymentDetails) {
        logger.info("Received payment processing request for ID: {}", paymentDetails.getProcessingId());

        PaymentStrategy paymentStrategy = paymentStrategies.get(paymentDetails.getPaymentMode());

        if (paymentStrategy == null) {
            logger.error("No payment strategy found for mode: {}", paymentDetails.getPaymentMode());
            paymentDetails.setStatus(PaymentStatus.FAILED);
            return;
        }

        try {
            paymentStrategy.pay(paymentDetails);
            logger.info("Payment processing completed for ID: {}", paymentDetails.getProcessingId());
        } catch (Exception e) {
            logger.error("Payment processing failed for ID: {}", paymentDetails.getProcessingId(), e);
            paymentDetails.setStatus(PaymentStatus.FAILED);
        }
    }
}
