package com.nelumbo.parking.exceptions;

public class ParkingNotFoundException extends RuntimeException {
    public ParkingNotFoundException(String message) {
        super(message);
    }

    public ParkingNotFoundException(RuntimeException e) {
        super(e);
    }
}

