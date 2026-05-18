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

    public boolean lockSeats(Show show, List<Seat> seats, User user) {
        for (int i = 0; i < seats.size(); i++) {
            Seat seat = seats.get(i);
            if (seat.getStatus() == SeatStatus.BOOKED || lockedSeats.containsKey(getLockKey(show, seat))) {
                return false;
            }
        }

        for (int i = 0; i < seats.size(); i++) {
            Seat seat = seats.get(i);
            lockedSeats.put(getLockKey(show, seat), user.getId());
        }
        return true;
    }

    public void unlockSeats(Show show, List<Seat> seats) {
        for (int i = 0; i < seats.size(); i++) {
            lockedSeats.remove(getLockKey(show, seats.get(i)));
        }
    }

    private String getLockKey(Show show, Seat seat) {
        return show.getId() + "_" + seat.getId();
    }
}
