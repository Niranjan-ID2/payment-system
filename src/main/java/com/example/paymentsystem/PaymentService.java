package com.example.paymentsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final Map<UUID, PaymentDetails> paymentRepository = new ConcurrentHashMap<>();
    private final PaymentProcessor paymentProcessor;

    public PaymentService(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public UUID initiatePayment(PaymentRequest paymentRequest) {
        UUID processingId = UUID.randomUUID();
        PaymentDetails paymentDetails = new PaymentDetails(
                processingId,
                paymentRequest.getUserId(),
                paymentRequest.getPaymentMode(),
                paymentRequest.getAmount(),
                paymentRequest.getCurrency(),
                paymentRequest.getRemarks()
        );

        // Record transaction before payment
        paymentRepository.put(processingId, paymentDetails);
        logger.info("Transaction recorded for processing ID: {}", processingId);

        // Post message to SQS (simulated)
        logger.info("Posting message to SQS for processing ID: {}", processingId);
        paymentProcessor.processPayment(paymentDetails);

        return processingId;
    }

    public PaymentDetails getPaymentStatus(UUID processingId) {
        return paymentRepository.get(processingId);
    }
}
