package service;

import enums.PaymentStatus;
import model.PaymentRequest;
import payment.PaymentStrategy;
import payment.PaymentStrategyFactory;

public class PaymentService {
    private PaymentStrategyFactory paymentStrategyFactory;

    public PaymentService(PaymentStrategyFactory paymentStrategyFactory) {
        this.paymentStrategyFactory = paymentStrategyFactory;
    }

    public PaymentStatus makePayment(PaymentRequest paymentRequest) {
        if (paymentRequest == null) {
            throw new IllegalArgumentException("Payment request cannot be null");
        }
        if (paymentRequest.getAmount() <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }
        PaymentStrategy strategy = paymentStrategyFactory.getPaymentStrategy(paymentRequest.getPaymentMode());
        return strategy.pay(paymentRequest);
    }
}
