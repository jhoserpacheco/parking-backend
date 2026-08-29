package com.nelumbo.parking.service.observer.event;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Evento de dominio emitido cuando un vehículo ingresa al parqueadero.
 * Parte de la implementación del Patrón GoF Observer.
 */
@Getter
@Builder
@ToString
public class VehicleEntryEvent {
    private final UUID parkingId;
    private final String vehiclePlate;
    private final String vehicleModel;
    private final LocalDateTime entryDate;
    private final String socioEmail;
}
