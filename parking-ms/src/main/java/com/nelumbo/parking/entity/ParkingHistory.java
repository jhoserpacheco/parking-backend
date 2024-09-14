package com.nelumbo.parking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "parking_history")
public class ParkingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "parking_id", nullable = false)
    private Parking parking;

    @ManyToOne
    @JoinColumn(name = "vehicle_plate", referencedColumnName = "vehicle_plate", nullable = false)
    private Vehicle vehicle;

    @Column(name = "total_cost")
    private double totalCost;

    @Column(name = "entry_date")
    private LocalDateTime entryDate;

    @Column(name = "exit_date")
    private LocalDateTime exitDate;

}
