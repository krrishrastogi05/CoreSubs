import enums.PaymentMode;
import java.util.ArrayList;
import java.util.List;
import model.Booking;
import model.BookingRequest;
import model.City;
import model.Movie;
import model.Screen;
import model.Seat;
import model.Show;
import model.Theatre;
import model.User;
import payment.PaymentStrategyFactory;
import service.BookingService;
import service.CatalogService;
import service.PaymentService;
import service.SeatLockService;

public class Main {
    public static void main(String[] args) {
        CatalogService catalogService = new CatalogService();
        SeatLockService seatLockService = new SeatLockService();
        PaymentService paymentService = new PaymentService(new PaymentStrategyFactory());
        BookingService bookingService = new BookingService(
                catalogService, seatLockService, paymentService);

        User user = new User("user1", "Aman", "aman@example.com");

        City bengaluru = new City("city1", "Bengaluru");
        catalogService.addCity(bengaluru);

        Theatre theatre = new Theatre("theatre1", "PVR Orion", bengaluru);
        catalogService.addTheatre(theatre);

        Screen screen = new Screen("screen1", "Audi 1");
        screen.addSeat(new Seat("seat1", "A1"));
        screen.addSeat(new Seat("seat2", "A2"));
        screen.addSeat(new Seat("seat3", "A3"));
        screen.addSeat(new Seat("seat4", "A4"));
        theatre.addScreen(screen);

        Movie movie = new Movie("movie1", "Interstellar", 169);
        catalogService.addMovie(movie);

        Show show = new Show("show1", movie, theatre, screen, "18 May 2026 07:30 PM", 250.0);
        catalogService.addShow(show);

        System.out.println("Searching shows in Bengaluru for Interstellar");
        List<Show> shows = catalogService.searchShows("city1", "movie1");
        printShows(shows);

        System.out.println();
        System.out.println("Successful booking flow");
        List<String> successSeats = new ArrayList<String>();
        successSeats.add("seat1");
        successSeats.add("seat2");
        Booking confirmedBooking = bookingService.createBooking(new BookingRequest(
                user, "show1", successSeats, PaymentMode.UPI, true));
        printBooking(confirmedBooking);

        System.out.println();
        System.out.println("Payment failure flow");
        List<String> failureSeats = new ArrayList<String>();
        failureSeats.add("seat3");
        Booking failedBooking = bookingService.createBooking(new BookingRequest(
                user, "show1", failureSeats, PaymentMode.CARD, false));
        printBooking(failedBooking);

        System.out.println();
        System.out.println("Cancellation flow");
        Booking cancelledBooking = bookingService.cancelBooking(confirmedBooking.getId(), user);
        printBooking(cancelledBooking);

        System.out.println();
        System.out.println("Booking seat again after cancellation");
        List<String> retrySeats = new ArrayList<String>();
        retrySeats.add("seat1");
        Booking retryBooking = bookingService.createBooking(new BookingRequest(
                user, "show1", retrySeats, PaymentMode.CARD, true));
        printBooking(retryBooking);
    }

    private static void printShows(List<Show> shows) {
        for (int i = 0; i < shows.size(); i++) {
            Show show = shows.get(i);
            System.out.println(show.getId() + " | " + show.getMovie().getTitle()
                    + " | " + show.getTheatre().getName()
                    + " | " + show.getScreen().getName()
                    + " | " + show.getStartTime());
        }
    }

    private static void printBooking(Booking booking) {
        System.out.println("Booking Id: " + booking.getId());
        System.out.println("Status: " + booking.getBookingStatus());
        System.out.println("Payment: " + booking.getPaymentStatus());
        System.out.println("Amount: " + booking.getAmount());
        System.out.println("Seats: " + getSeatNumbers(booking.getSeats()));
    }

    private static String getSeatNumbers(List<Seat> seats) {
        String result = "";
        for (int i = 0; i < seats.size(); i++) {
            if (i > 0) {
                result = result + ", ";
            }
            result = result + seats.get(i).getSeatNumber();
        }
        return result;
    }
}
