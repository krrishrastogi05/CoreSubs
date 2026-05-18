package model;

import enums.PaymentMethod;
import enums.PaymentStatus;


public class Payment {
    private String paymentId;
    private String orderId;
    private String userId;
    private double amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private String transactionId;


    public Payment(String paymentId, String orderId, String userId, double amount, PaymentMethod paymentMethod
    ) {
            
        this.paymentStatus = paymentStatus.PENDING;
        
    }
}