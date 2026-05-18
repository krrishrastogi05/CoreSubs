package model;

import enums.BookingStatus;
import enums.PaymentStatus;
import java.util.List;

public class Booking {
    private String id;
    private User user;
    private Show show;
    private List<Seat> seats;
    private double amount;
    private BookingStatus bookingStatus;
    private PaymentStatus paymentStatus;

    public Booking(String id, User user, Show show, List<Seat> seats, double amount) {
        this.id = id;
        this.user = user;
        this.show = show;
        this.seats = seats;
        this.amount = amount;
        this.bookingStatus = BookingStatus.CREATED;
        this.paymentStatus = PaymentStatus.FAILED;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Show getShow() {
        return show;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public double getAmount() {
        return amount;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
