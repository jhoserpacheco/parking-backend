package com.nelumbo.parking.exceptions;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(String message) {
        super(message);
    }

    public VehicleNotFoundException(RuntimeException e) {
        super(e);
    }
}