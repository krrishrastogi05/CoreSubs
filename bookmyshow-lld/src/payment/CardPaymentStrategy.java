package payment;

import enums.PaymentStatus;

public class CardPaymentStrategy implements PaymentStrategy {
    public PaymentStatus pay(double amount, boolean forceSuccess) {
        if (forceSuccess) {
            return PaymentStatus.SUCCESS;
        }
        return PaymentStatus.FAILED;
    }
}
