package com.example.paymentsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
@Import(TestApplication.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    private PaymentRequest paymentRequest;
    private PaymentDetails paymentDetails;
    private UUID processingId;

    @BeforeEach
    void setUp() {
        processingId = UUID.randomUUID();

        paymentRequest = new PaymentRequest();
        paymentRequest.setUserId("test-user");
        paymentRequest.setPaymentMode("creditCard");
        paymentRequest.setAmount(new BigDecimal("100.00"));
        paymentRequest.setCurrency("USD");
        paymentRequest.setRemarks("Test payment");

        paymentDetails = new PaymentDetails(
                processingId,
                "test-user",
                "creditCard",
                new BigDecimal("100.00"),
                "USD",
                "Test payment"
        );
    }

    @Test
    void testInitiatePayment() throws Exception {
        when(paymentService.initiatePayment(any(PaymentRequest.class))).thenReturn(processingId);

        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(processingId.toString()));
    }

    @Test
    void testGetPaymentStatus_found() throws Exception {
        when(paymentService.getPaymentStatus(processingId)).thenReturn(paymentDetails);

        mockMvc.perform(get("/payments/{processingId}", processingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.processingId").value(processingId.toString()))
                .andExpect(jsonPath("$.userId").value("test-user"))
                .andExpect(jsonPath("$.amount").value(100.00));
    }

    @Test
    void testGetPaymentStatus_notFound() throws Exception {
        when(paymentService.getPaymentStatus(any(UUID.class))).thenReturn(null);

        mockMvc.perform(get("/payments/{processingId}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}
