package service;

import enums.PaymentMode;
import enums.PaymentStatus;
import payment.PaymentStrategy;
import payment.PaymentStrategyFactory;

public class PaymentService {
    private PaymentStrategyFactory paymentStrategyFactory;

    public PaymentService() {
        this.paymentStrategyFactory = new PaymentStrategyFactory();
    }

    public PaymentStatus makePayment(PaymentMode paymentMode, double amount, boolean forceSuccess) {
        PaymentStrategy strategy = paymentStrategyFactory.getPaymentStrategy(paymentMode);
        return strategy.pay(amount, forceSuccess);
    }
}
