package com.example.paymentsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("bankTransfer")
public class BankTransferPaymentStrategy implements PaymentStrategy {

    private static final Logger logger = LoggerFactory.getLogger(BankTransferPaymentStrategy.class);

    @Override
    public void pay(PaymentDetails paymentDetails) {
        logger.info("Processing Bank Transfer for processing ID: {}", paymentDetails.getProcessingId());
        // In a real application, you would integrate with a bank's API here
        paymentDetails.setStatus(PaymentStatus.SUCCESSFUL);
        logger.info("Bank Transfer successful for processing ID: {}", paymentDetails.getProcessingId());
    }
}
