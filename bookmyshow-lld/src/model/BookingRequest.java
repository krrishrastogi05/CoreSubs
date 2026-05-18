package model;

import enums.PaymentMode;
import java.util.List;

public class BookingRequest {
    private User user;
    private String showId;
    private List<String> seatIds;
    private PaymentMode paymentMode;
    private boolean forcePaymentSuccess;

    public BookingRequest(User user, String showId, List<String> seatIds,
                          PaymentMode paymentMode, boolean forcePaymentSuccess) {
        this.user = user;
        this.showId = showId;
        this.seatIds = seatIds;
        this.paymentMode = paymentMode;
        this.forcePaymentSuccess = forcePaymentSuccess;
    }

    public User getUser() {
        return user;
    }

    public String getShowId() {
        return showId;
    }

    public List<String> getSeatIds() {
        return seatIds;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public boolean isForcePaymentSuccess() {
        return forcePaymentSuccess;
    }
}
