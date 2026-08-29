package com.nelumbo.parking.service.observer;

import com.nelumbo.parking.feign.EmailParkingDto;
import com.nelumbo.parking.mgsbroker.RabbitQueueSender;
import com.nelumbo.parking.mgsbroker.event.RespuestaBroker;
import com.nelumbo.parking.service.IParkingService;
import com.nelumbo.parking.service.observer.event.VehicleEntryEvent;
import com.nelumbo.parking.service.observer.event.VehicleExitEvent;
import com.nelumbo.parking.service.observer.listener.ParkingCapacityObserver;
import com.nelumbo.parking.service.observer.listener.ParkingNotificationObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParkingObserverTest {

    @Mock
    private IParkingService parkingService;

    @Mock
    private RabbitQueueSender rabbitQueueSender;

    private ParkingCapacityObserver capacityObserver;
    private ParkingNotificationObserver notificationObserver;

    private final UUID parkingId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        capacityObserver = new ParkingCapacityObserver(parkingService);
        notificationObserver = new ParkingNotificationObserver(rabbitQueueSender);
    }

    @Test
    @DisplayName("ParkingCapacityObserver debe incrementar capacidad en evento de entrada")
    void capacityObserver_OnVehicleEntry_IncrementsCapacity() {
        VehicleEntryEvent event = VehicleEntryEvent.builder()
                .parkingId(parkingId)
                .vehiclePlate("ABC123")
                .vehicleModel("MAZDA 3")
                .entryDate(LocalDateTime.now())
                .socioEmail("socio@parking.com")
                .build();

        capacityObserver.onVehicleEntry(event);

        verify(parkingService, times(1)).updateCurrentCapacity(parkingId, true);
    }

    @Test
    @DisplayName("ParkingCapacityObserver debe decrementar capacidad en evento de salida")
    void capacityObserver_OnVehicleExit_DecrementsCapacity() {
        VehicleExitEvent event = VehicleExitEvent.builder()
                .parkingId(parkingId)
                .vehiclePlate("ABC123")
                .vehicleModel("MAZDA 3")
                .entryDate(LocalDateTime.now().minusHours(2))
                .exitDate(LocalDateTime.now())
                .totalCost(10000.0)
                .socioEmail("socio@parking.com")
                .build();

        capacityObserver.onVehicleExit(event);

        verify(parkingService, times(1)).updateCurrentCapacity(parkingId, false);
    }

    @Test
    @DisplayName("ParkingNotificationObserver debe despachar mensaje a RabbitMQ en evento de salida")
    void notificationObserver_OnVehicleExit_SendsRabbitMessage() {
        VehicleExitEvent event = VehicleExitEvent.builder()
                .parkingId(parkingId)
                .vehiclePlate("ABC123")
                .vehicleModel("MAZDA 3")
                .entryDate(LocalDateTime.now().minusHours(2))
                .exitDate(LocalDateTime.now())
                .totalCost(10000.0)
                .socioEmail("socio@parking.com")
                .build();

        when(rabbitQueueSender.send(any(EmailParkingDto.class)))
                .thenReturn(RespuestaBroker.builder().solicitudExito(true).mensajeRespuesta("Enviado").build());

        notificationObserver.onVehicleExit(event);

        ArgumentCaptor<EmailParkingDto> captor = ArgumentCaptor.forClass(EmailParkingDto.class);
        verify(rabbitQueueSender, times(1)).send(captor.capture());
        assertEquals("ABC123", captor.getValue().getVehiclePlate());
        assertEquals("socio@parking.com", captor.getValue().getEmailTo());
    }

    @Test
    @DisplayName("ParkingNotificationObserver debe ser resiliente ante fallos del broker")
    void notificationObserver_BrokerFailure_DoesNotThrowException() {
        VehicleEntryEvent event = VehicleEntryEvent.builder()
                .parkingId(parkingId)
                .vehiclePlate("ABC123")
                .vehicleModel("MAZDA 3")
                .entryDate(LocalDateTime.now())
                .socioEmail("socio@parking.com")
                .build();

        when(rabbitQueueSender.send(any(EmailParkingDto.class))).thenThrow(new RuntimeException("RabbitMQ indisponible"));

        // No debe lanzar excepción
        notificationObserver.onVehicleEntry(event);

        verify(rabbitQueueSender, times(1)).send(any(EmailParkingDto.class));
    }
}
