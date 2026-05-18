package payment;

import enums.PaymentStatus;
import model.PaymentRequest;

public class UpiPaymentStrategy implements PaymentStrategy {
    public PaymentStatus pay(PaymentRequest paymentRequest) {
        if (paymentRequest.isForceSuccess()) {
            return PaymentStatus.SUCCESS;
        }
        return PaymentStatus.FAILED;
    }
}
