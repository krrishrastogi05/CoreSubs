package model;

import enums.SeatStatus;

public class Seat {
    private String id;
    private String seatNumber;
    private SeatStatus status;

    public Seat(String id, String seatNumber) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.status = SeatStatus.AVAILABLE;
    }

    public String getId() {
        return id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }
}
