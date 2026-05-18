package service;

import enums.SeatStatus;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Seat;
import model.Show;
import model.User;

public class SeatLockService {
    private Map<String, String> lockedSeats;

    public SeatLockService() {
        this.lockedSeats = new HashMap<String, String>();
    }

    public boolean areSeatsAvailable(Show show, List<Seat> seats) {
        validate(show, seats, null);
        for (int i = 0; i < seats.size(); i++) {
            Seat seat = seats.get(i);
            if (seat.getStatus() == SeatStatus.BOOKED) {
                return false;
            }
            if (lockedSeats.get(getLockKey(show, seat)) != null) {
                return false;
            }
        }
        return true;
    }

    public boolean lockSeats(Show show, List<Seat> seats, User user) {
        validate(show, seats, user);
        if (!areSeatsAvailable(show, seats)) {
            return false;
        }
        for (int i = 0; i < seats.size(); i++) {
            Seat seat = seats.get(i);
            lockedSeats.put(getLockKey(show, seat), user.getId());
        }
        return true;
    }

    public void unlockSeats(Show show, List<Seat> seats, User user) {
        validate(show, seats, user);
        for (int i = 0; i < seats.size(); i++) {
            Seat seat = seats.get(i);
            String key = getLockKey(show, seat);
            String lockedBy = lockedSeats.get(key);
            if (user.getId().equals(lockedBy)) {
                lockedSeats.remove(key);
            }
        }
    }

    public void confirmSeats(Show show, List<Seat> seats, User user) {
        validate(show, seats, user);
        for (int i = 0; i < seats.size(); i++) {
            Seat seat = seats.get(i);
            String key = getLockKey(show, seat);
            String lockedBy = lockedSeats.get(key);
            if (!user.getId().equals(lockedBy)) {
                throw new IllegalStateException("Seat is not locked by this user: " + seat.getSeatNumber());
            }
            seat.setStatus(SeatStatus.BOOKED);
            lockedSeats.remove(key);
        }
    }

    private String getLockKey(Show show, Seat seat) {
        return show.getId() + "_" + seat.getId();
    }

    private void validate(Show show, List<Seat> seats, User user) {
        if (show == null) {
            throw new IllegalArgumentException("Show is required");
        }
        if (seats == null || seats.size() == 0) {
            throw new IllegalArgumentException("At least one seat is required");
        }
        if (user != null && (user.getId() == null || user.getId().length() == 0)) {
            throw new IllegalArgumentException("User id is required");
        }
    }
}
