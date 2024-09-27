package com.nelumbo.parking.projection;

public interface EstimateCostProjection {

    String getParkingName();
    double getCost();
    String getMaxCapacity();
    double getEstimateCost();

}