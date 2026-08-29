package com.nelumbo.parking.service.observer.listener;

import com.nelumbo.parking.feign.EmailParkingDto;
import com.nelumbo.parking.mgsbroker.RabbitQueueSender;
import com.nelumbo.parking.service.observer.event.VehicleEntryEvent;
import com.nelumbo.parking.service.observer.event.VehicleExitEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Observador encargado de despachar notificaciones asíncronas hacia RabbitMQ
 * cuando ocurren eventos en el ciclo de vida vehicular.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ParkingNotificationObserver {

    private final RabbitQueueSender rabbitQueueSender;

    @EventListener
    public void onVehicleEntry(VehicleEntryEvent event) {
        log.info("[Observer: Notification] Procesando notificación de ingreso para placa: {}", event.getVehiclePlate());
        try {
            EmailParkingDto emailDto = new EmailParkingDto();
            emailDto.setEmailTo(event.getSocioEmail() != null ? event.getSocioEmail() : "admin@parking.com");
            emailDto.setSubject("Notificación de Ingreso - Parqueadero");
            emailDto.setVehiclePlate(event.getVehiclePlate());
            emailDto.setParkingName("Parqueadero ID: " + event.getParkingId());
            emailDto.setText(String.format("El vehículo con placa %s (%s) ha ingresado a las %s.",
                    event.getVehiclePlate(),
                    event.getVehicleModel() != null ? event.getVehicleModel() : "Vehículo",
                    event.getEntryDate()));

            rabbitQueueSender.send(emailDto);
            log.info("[Observer: Notification] Notificación de ingreso enviada exitosamente a RabbitMQ para placa: {}", event.getVehiclePlate());
        } catch (Exception e) {
            log.warn("[Observer: Notification] No fue posible enviar la notificación a RabbitMQ (resiliencia activada): {}", e.getMessage());
        }
    }

    @EventListener
    public void onVehicleExit(VehicleExitEvent event) {
        log.info("[Observer: Notification] Procesando notificación de salida para placa: {}, Costo: ${}",
                event.getVehiclePlate(), event.getTotalCost());
        try {
            EmailParkingDto emailDto = new EmailParkingDto();
            emailDto.setEmailTo(event.getSocioEmail() != null ? event.getSocioEmail() : "admin@parking.com");
            emailDto.setSubject("Notificación de Salida y Cobro - Parqueadero");
            emailDto.setVehiclePlate(event.getVehiclePlate());
            emailDto.setParkingName("Parqueadero ID: " + event.getParkingId());
            emailDto.setText(String.format("El vehículo con placa %s ha salido a las %s. Total liquidado: $%.2f.",
                    event.getVehiclePlate(),
                    event.getExitDate(),
                    event.getTotalCost() != null ? event.getTotalCost() : 0.0));

            rabbitQueueSender.send(emailDto);
            log.info("[Observer: Notification] Notificación de salida enviada exitosamente a RabbitMQ para placa: {}", event.getVehiclePlate());
        } catch (Exception e) {
            log.warn("[Observer: Notification] No fue posible enviar la notificación a RabbitMQ (resiliencia activada): {}", e.getMessage());
        }
    }
}
