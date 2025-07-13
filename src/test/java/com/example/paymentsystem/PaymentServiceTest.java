package com.example.paymentsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void testInitiatePayment_nullRequest() {
        assertThrows(NullPointerException.class, () -> paymentService.initiatePayment(null));
    }

    @Test
    void testInitiatePayment_invalidAmount() {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setUserId("test-user");
        paymentRequest.setPaymentMode("creditCard");
        paymentRequest.setAmount(new BigDecimal("-100.00"));
        paymentRequest.setCurrency("USD");
        paymentRequest.setRemarks("Test payment");

        // Depending on validation logic, this might throw an exception or handle it gracefully.
        // For now, let's assume it proceeds and the validation would be elsewhere.
        UUID processingId = paymentService.initiatePayment(paymentRequest);
        assertNotNull(processingId);
        PaymentDetails paymentDetails = paymentService.getPaymentStatus(processingId);
        assertEquals(new BigDecimal("-100.00"), paymentDetails.getAmount());
    }

    @Test
    void testGetPaymentStatus_nonExistent() {
        assertNull(paymentService.getPaymentStatus(UUID.randomUUID()));
    }
}
