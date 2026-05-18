package model;

import java.util.List;

public class Show {
    private String id;
    private Movie movie;
    private Theatre theatre;
    private Screen screen;
    private String startTime;
    private double pricePerSeat;

    public Show(String id, Movie movie, Theatre theatre, Screen screen, String startTime, double pricePerSeat) {
        this.id = id;
        this.movie = movie;
        this.theatre = theatre;
        this.screen = screen;
        this.startTime = startTime;
        this.pricePerSeat = pricePerSeat;
    }

    public Seat findSeatById(String seatId) {
        List<Seat> seats = screen.getSeats();
        for (int i = 0; i < seats.size(); i++) {
            Seat seat = seats.get(i);
            if (seat.getId().equals(seatId)) {
                return seat;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public Screen getScreen() {
        return screen;
    }

    public String getStartTime() {
        return startTime;
    }

    public double getPricePerSeat() {
        return pricePerSeat;
    }
}
