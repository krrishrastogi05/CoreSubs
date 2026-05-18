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
        User user = request.getUser();
        Show show = catalogService.getShow(request.getShowId());
        if (show == null) {
            throw new IllegalArgumentException("Show not found");
        }

        List<Seat> selectedSeats = getSelectedSeats(show, request.getSeatIds());
        if (!seatLockService.lockSeats(show, selectedSeats, user)) {
            throw new IllegalStateException("Seats are not available");
        }

        double amount = show.getPricePerSeat() * selectedSeats.size();
        String bookingId = "booking" + bookingCounter;
        bookingCounter++;

        Booking booking = new Booking(bookingId, user, show, selectedSeats, amount);
        PaymentStatus paymentStatus = paymentService.makePayment(
                request.getPaymentMode(), amount, request.isForcePaymentSuccess());
        booking.setPaymentStatus(paymentStatus);

        if (paymentStatus == PaymentStatus.SUCCESS) {
            booking.setBookingStatus(BookingStatus.CONFIRMED);
            markSeatsBooked(selectedSeats);
            seatLockService.unlockSeats(show, selectedSeats);
            System.out.println("Booking confirmed: " + booking.getId());
        } else {
            booking.setBookingStatus(BookingStatus.FAILED);
            seatLockService.unlockSeats(show, selectedSeats);
        }

        bookings.put(booking.getId(), booking);
        return booking;
    }

    public Booking cancelBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking == null || booking.getBookingStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Booking cannot be cancelled");
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

    private void markSeatsBooked(List<Seat> seats) {
        for (int i = 0; i < seats.size(); i++) {
            seats.get(i).setStatus(SeatStatus.BOOKED);
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

}
