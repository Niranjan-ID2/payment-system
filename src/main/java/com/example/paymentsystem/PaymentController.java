package com.example.paymentsystem;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<UUID> initiatePayment(@RequestBody PaymentRequest paymentRequest) {
        UUID processingId = paymentService.initiatePayment(paymentRequest);
        return ResponseEntity.ok(processingId);
    }

    @GetMapping("/{processingId}")
    public ResponseEntity<PaymentDetails> getPaymentStatus(@PathVariable UUID processingId) {
        PaymentDetails paymentDetails = paymentService.getPaymentStatus(processingId);
        if (paymentDetails == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paymentDetails);
    }
}
