package model;

import enums.PaymentMode;

public class PaymentRequest {
    private String bookingId;
    private String userId;
    private double amount;
    private PaymentMode paymentMode;
    private boolean forceSuccess;

    public PaymentRequest(String bookingId, String userId, double amount, PaymentMode paymentMode, boolean forceSuccess) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.forceSuccess = forceSuccess;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public boolean isForceSuccess() {
        return forceSuccess;
    }
}
