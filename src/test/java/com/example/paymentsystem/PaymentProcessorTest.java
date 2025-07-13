package com.example.paymentsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PaymentProcessorTest {

    @Mock
    private PaymentStrategy gpayStrategy;

    @Mock
    private PaymentStrategy creditCardStrategy;

    private PaymentProcessor paymentProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Map<String, PaymentStrategy> strategies = new HashMap<>();
        strategies.put("gpay", gpayStrategy);
        strategies.put("creditCard", creditCardStrategy);
        paymentProcessor = new PaymentProcessor(strategies);
    }

    @Test
    void testProcessPayment_gpay() {
        PaymentDetails paymentDetails = new PaymentDetails(
                UUID.randomUUID(),
                "test-user",
                "gpay",
                new BigDecimal("50.00"),
                "EUR",
                "Test GPay"
        );

        paymentProcessor.processPayment(paymentDetails);

        verify(gpayStrategy).pay(paymentDetails);
    }

    @Test
    void testProcessPayment_creditCard() {
        PaymentDetails paymentDetails = new PaymentDetails(
                UUID.randomUUID(),
                "test-user",
                "creditCard",
                new BigDecimal("150.00"),
                "GBP",
                "Test Credit Card"
        );

        paymentProcessor.processPayment(paymentDetails);

        verify(creditCardStrategy).pay(paymentDetails);
    }

    @Test
    void testProcessPayment_unknownStrategy() {
        PaymentDetails paymentDetails = new PaymentDetails(
                UUID.randomUUID(),
                "test-user",
                "unknown",
                new BigDecimal("200.00"),
                "JPY",
                "Test Unknown"
        );

        paymentProcessor.processPayment(paymentDetails);

        assertEquals(PaymentStatus.FAILED, paymentDetails.getStatus());
    }
}
