package com.example.paymentsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class PaymentServiceTest {

    @Mock
    private PaymentProcessor paymentProcessor;

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentService = new PaymentService(paymentProcessor);
    }

    @Test
    void testInitiatePayment() {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setUserId("test-user");
        paymentRequest.setPaymentMode("creditCard");
        paymentRequest.setAmount(new BigDecimal("100.00"));
        paymentRequest.setCurrency("USD");
        paymentRequest.setRemarks("Test payment");

        UUID processingId = paymentService.initiatePayment(paymentRequest);

        assertNotNull(processingId);

        PaymentDetails paymentDetails = paymentService.getPaymentStatus(processingId);
        assertNotNull(paymentDetails);
        assertEquals(processingId, paymentDetails.getProcessingId());
        assertEquals("test-user", paymentDetails.getUserId());
        assertEquals("creditCard", paymentDetails.getPaymentMode());
        assertEquals(new BigDecimal("100.00"), paymentDetails.getAmount());
        assertEquals("USD", paymentDetails.getCurrency());
        assertEquals("Test payment", paymentDetails.getRemarks());
        assertEquals(PaymentStatus.PENDING, paymentDetails.getStatus());

        verify(paymentProcessor).processPayment(any(PaymentDetails.class));
    }
}
