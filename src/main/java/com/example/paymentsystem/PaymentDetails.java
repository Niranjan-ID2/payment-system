package com.example.paymentsystem;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentDetails {

    private UUID processingId;
    private String userId;
    private String paymentMode;
    private BigDecimal amount;
    private String currency;
    private String remarks;
    private PaymentStatus status;

    public PaymentDetails(UUID processingId, String userId, String paymentMode, BigDecimal amount, String currency, String remarks) {
        this.processingId = processingId;
        this.userId = userId;
        this.paymentMode = paymentMode;
        this.amount = amount;
        this.currency = currency;
        this.remarks = remarks;
        this.status = PaymentStatus.PENDING;
    }

    // Getters and setters

    public UUID getProcessingId() {
        return processingId;
    }

    public void setProcessingId(UUID processingId) {
        this.processingId = processingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
