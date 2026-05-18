package payment;

import enums.PaymentMode;

public class PaymentStrategyFactory {
    public PaymentStrategy getPaymentStrategy(PaymentMode paymentMode) {
        if (paymentMode == PaymentMode.UPI) {
            return new UpiPaymentStrategy();
        }
        if (paymentMode == PaymentMode.CARD) {
            return new CardPaymentStrategy();
        }
        throw new IllegalArgumentException("Unsupported payment mode");
    }
}
