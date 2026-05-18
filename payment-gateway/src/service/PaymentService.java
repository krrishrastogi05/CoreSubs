package service;

import model.Payment;

public class PaymentService {
    

    private PaymentStrategyFactory factory = new PaymentStrategyFactory();

    public Payment initiatePayment() {
        //i should know the 
        PaymentStrategy strategy = factory.getStrategy(request.getPaymentMethod());
        Payment payment = strategy.pay(request);
        payments.put(payment.getPaymentId)
    }
}
