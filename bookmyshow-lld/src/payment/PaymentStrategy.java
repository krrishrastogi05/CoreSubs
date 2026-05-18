package payment;

import enums.PaymentStatus;

public interface PaymentStrategy {
    PaymentStatus pay(double amount, boolean forceSuccess);
}
