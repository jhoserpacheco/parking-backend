package com.nelumbo.parking.service.observer.listener;

import com.nelumbo.parking.service.IParkingService;
import com.nelumbo.parking.service.observer.event.VehicleEntryEvent;
import com.nelumbo.parking.service.observer.event.VehicleExitEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Observador encargado de mantener sincronizada la ocupación / capacidad del parqueadero
 * reaccionando de forma desacoplada a los eventos del ciclo de vida del vehículo.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ParkingCapacityObserver {

    private final IParkingService parkingService;

    @EventListener
    public void onVehicleEntry(VehicleEntryEvent event) {
        log.info("[Observer: Capacity] Incrementando ocupación en parqueadero: {} por ingreso de vehículo: {}",
                event.getParkingId(), event.getVehiclePlate());
        parkingService.updateCurrentCapacity(event.getParkingId(), true);
    }

    @EventListener
    public void onVehicleExit(VehicleExitEvent event) {
        log.info("[Observer: Capacity] Decrementando ocupación en parqueadero: {} por salida de vehículo: {}",
                event.getParkingId(), event.getVehiclePlate());
        parkingService.updateCurrentCapacity(event.getParkingId(), false);
    }
}
