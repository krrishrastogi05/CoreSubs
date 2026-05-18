package payment;

import enums.PaymentStatus;
import model.PaymentRequest;

public interface PaymentStrategy {
    PaymentStatus pay(PaymentRequest paymentRequest);
}
