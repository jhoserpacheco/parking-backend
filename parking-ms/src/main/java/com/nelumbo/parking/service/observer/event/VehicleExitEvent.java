package com.nelumbo.parking.service.observer.event;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Evento de dominio emitido cuando un vehículo sale del parqueadero y se liquida su cobro.
 * Parte de la implementación del Patrón GoF Observer.
 */
@Getter
@Builder
@ToString
public class VehicleExitEvent {
    private final UUID parkingId;
    private final String vehiclePlate;
    private final String vehicleModel;
    private final LocalDateTime entryDate;
    private final LocalDateTime exitDate;
    private final Double totalCost;
    private final String socioEmail;
}
