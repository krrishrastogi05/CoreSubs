package service;

import enums.BookingStatus;
import enums.PaymentStatus;
import enums.SeatStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Booking;
import model.BookingRequest;
import model.PaymentRequest;
import model.Seat;
import model.Show;
import model.User;

public class BookingService {
    private CatalogService catalogService;
    private SeatLockService seatLockService;
    private PaymentService paymentService;
    private Map<String, Booking> bookings;
    private int bookingCounter;

    public BookingService(CatalogService catalogService, SeatLockService seatLockService,
                          PaymentService paymentService) {
        this.catalogService = catalogService;
        this.seatLockService = seatLockService;
        this.paymentService = paymentService;
        this.bookings = new HashMap<String, Booking>();
        this.bookingCounter = 1;
    }

    public Booking createBooking(BookingRequest request) {
        validateCreateBookingInput(request);

        User user = request.getUser();
        Show show = catalogService.getShow(request.getShowId());
        if (show == null) {
            throw new IllegalArgumentException("Show not found");
        }

        List<Seat> selectedSeats = getSelectedSeats(show, request.getSeatIds());
        if (!seatLockService.areSeatsAvailable(show, selectedSeats)) {
            throw new IllegalStateException("Selected seats are not available");
        }

        boolean locked = seatLockService.lockSeats(show, selectedSeats, user);
        if (!locked) {
            throw new IllegalStateException("Could not lock selected seats");
        }

        double amount = calculateAmount(show, selectedSeats);
        String bookingId = "booking" + bookingCounter;
        bookingCounter++;

        Booking booking = new Booking(bookingId, user, show, selectedSeats, amount);
        PaymentRequest paymentRequest = new PaymentRequest(
                bookingId, user.getId(), amount, request.getPaymentMode(), request.isForcePaymentSuccess());

        PaymentStatus paymentStatus = paymentService.makePayment(paymentRequest);
        booking.setPaymentStatus(paymentStatus);

        if (paymentStatus == PaymentStatus.SUCCESS) {
            booking.setBookingStatus(BookingStatus.CONFIRMED);
            seatLockService.confirmSeats(show, selectedSeats, user);
            System.out.println("Booking confirmed: " + booking.getId());
        } else {
            booking.setBookingStatus(BookingStatus.FAILED);
            seatLockService.unlockSeats(show, selectedSeats, user);
        }

        bookings.put(booking.getId(), booking);
        return booking;
    }

    public Booking cancelBooking(String bookingId, User user) {
        if (bookingId == null || bookingId.length() == 0) {
            throw new IllegalArgumentException("Booking id is required");
        }
        if (user == null || user.getId() == null || user.getId().length() == 0) {
            throw new IllegalArgumentException("User is required");
        }

        Booking booking = bookings.get(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found");
        }
        if (!booking.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("Only booking owner can cancel booking");
        }
        if (booking.getBookingStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Only confirmed booking can be cancelled");
        }

        List<Seat> seats = booking.getSeats();
        for (int i = 0; i < seats.size(); i++) {
            seats.get(i).setStatus(SeatStatus.AVAILABLE);
        }
        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookings.put(booking.getId(), booking);
        System.out.println("Booking cancelled: " + booking.getId());
        return booking;
    }

    public Booking getBooking(String bookingId) {
        if (bookingId == null || bookingId.length() == 0) {
            throw new IllegalArgumentException("Booking id is required");
        }
        return bookings.get(bookingId);
    }

    private void validateCreateBookingInput(BookingRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Booking request is required");
        }
        User user = request.getUser();
        if (user == null || user.getId() == null || user.getId().length() == 0) {
            throw new IllegalArgumentException("User is required");
        }
        if (request.getShowId() == null || request.getShowId().length() == 0) {
            throw new IllegalArgumentException("Show id is required");
        }
        if (request.getSeatIds() == null || request.getSeatIds().size() == 0) {
            throw new IllegalArgumentException("At least one seat id is required");
        }
        if (request.getPaymentMode() == null) {
            throw new IllegalArgumentException("Payment mode is required");
        }
    }

    private List<Seat> getSelectedSeats(Show show, List<String> seatIds) {
        List<Seat> selectedSeats = new ArrayList<Seat>();
        for (int i = 0; i < seatIds.size(); i++) {
            Seat seat = show.findSeatById(seatIds.get(i));
            if (seat == null) {
                throw new IllegalArgumentException("Seat not found: " + seatIds.get(i));
            }
            selectedSeats.add(seat);
        }
        return selectedSeats;
    }

    private double calculateAmount(Show show, List<Seat> seats) {
        return show.getPricePerSeat() * seats.size();
    }
}
