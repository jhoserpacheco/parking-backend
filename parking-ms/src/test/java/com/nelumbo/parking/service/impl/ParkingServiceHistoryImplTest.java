package com.nelumbo.parking.service.impl;

import com.nelumbo.parking.dto.ParkingDto;
import com.nelumbo.parking.dto.RegisterParkingDto;
import com.nelumbo.parking.dto.VehicleDto;
import com.nelumbo.parking.entity.Parking;
import com.nelumbo.parking.entity.ParkingHistory;
import com.nelumbo.parking.entity.Vehicle;
import com.nelumbo.parking.repository.IParkingHistoryRepository;
import com.nelumbo.parking.service.observer.event.VehicleEntryEvent;
import com.nelumbo.parking.service.observer.event.VehicleExitEvent;
import com.nelumbo.parking.service.strategy.ParkingTariffStrategy;
import com.nelumbo.parking.service.strategy.TariffStrategyFactory;
import com.nelumbo.parking.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParkingServiceHistoryImplTest {

    @Mock
    private IParkingHistoryRepository parkingHistoryRepository;

    @Mock
    private VehicleServiceImpl vehicleService;

    @Mock
    private ParkingServiceImpl parkingService;

    @Mock
    private TariffStrategyFactory tariffStrategyFactory;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private ParkingTariffStrategy tariffStrategy;

    @InjectMocks
    private ParkingServiceHistoryImpl parkingServiceHistory;

    private final UUID parkingId = UUID.randomUUID();
    private ParkingDto parkingDto;
    private VehicleDto vehicleDto;

    @BeforeEach
    void setUp() {
        parkingDto = new ParkingDto();
        parkingDto.setId(parkingId);
        parkingDto.setName("Parqueadero Central");
        parkingDto.setCostHour(5000.0);
        parkingDto.setEmailUser("socio@parking.com");

        vehicleDto = new VehicleDto();
        vehicleDto.setVehiclePlate("XYZ789");
        vehicleDto.setModel("TOYOTA COROLLA");
        vehicleDto.setIdParking(parkingId);
    }

    @Test
    @DisplayName("registerEntry debe guardar historial y publicar VehicleEntryEvent")
    void registerEntry_Success_PublishesEntryEvent() {
        when(parkingService.findById(parkingId)).thenReturn(Optional.of(parkingDto));
        doNothing().when(parkingService).isParkingSocioAsociated(parkingId);
        when(vehicleService.findByVehiclePlate("XYZ789")).thenReturn(Optional.empty());
        when(parkingHistoryRepository.findByVehicleVehiclePlateAndExitDateIsNull("XYZ789")).thenReturn(Optional.empty());
        when(vehicleService.save(any(VehicleDto.class))).thenReturn(vehicleDto);

        ParkingHistory savedHistory = new ParkingHistory();
        savedHistory.setEntryDate(LocalDateTime.now());
        when(parkingHistoryRepository.save(any(ParkingHistory.class))).thenReturn(savedHistory);

        RegisterParkingDto result = parkingServiceHistory.registerEntry(vehicleDto, parkingId);

        assertNotNull(result);
        assertEquals(Constants.Message.REGISTER_ENTRY, result.getMessage());
        assertEquals("XYZ789", result.getVehiclePlate());

        // Verificar que se disparó el evento Observer
        verify(eventPublisher, times(1)).publishEvent(any(VehicleEntryEvent.class));
    }

    @Test
    @DisplayName("registerExit debe calcular costo con Strategy y publicar VehicleExitEvent")
    void registerExit_Success_UsesStrategyAndPublishesExitEvent() {
        doNothing().when(parkingService).isParkingSocioAsociated(parkingId);
        when(vehicleService.findByVehiclePlate("XYZ789")).thenReturn(Optional.of(vehicleDto));
        when(parkingService.findById(parkingId)).thenReturn(Optional.of(parkingDto));

        LocalDateTime entryDate = LocalDateTime.now().minusHours(2);
        Parking parkingEntity = new Parking();
        parkingEntity.setId(parkingId);
        parkingEntity.setCostHour(5000.0);

        Vehicle vehicleEntity = new Vehicle();
        vehicleEntity.setVehiclePlate("XYZ789");
        vehicleEntity.setModel("TOYOTA COROLLA");

        ParkingHistory historyEntity = new ParkingHistory();
        historyEntity.setParking(parkingEntity);
        historyEntity.setVehicle(vehicleEntity);
        historyEntity.setEntryDate(entryDate);

        when(parkingHistoryRepository.findByVehicleVehiclePlateAndParkingIdAndExitDateIsNull("XYZ789", parkingId))
                .thenReturn(Optional.of(historyEntity));

        when(tariffStrategyFactory.getStrategy(eq("TOYOTA COROLLA"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(tariffStrategy);
        when(tariffStrategy.calculateCost(any(LocalDateTime.class), any(LocalDateTime.class), eq(5000.0)))
                .thenReturn(10000.0);

        when(parkingHistoryRepository.save(any(ParkingHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RegisterParkingDto result = parkingServiceHistory.registerExit(vehicleDto, parkingId);

        assertNotNull(result);
        assertEquals(Constants.Message.REGISTER_EXIT, result.getMessage());

        // Verificar que se utilizó la estrategia para liquidar
        verify(tariffStrategyFactory, times(1)).getStrategy(eq("TOYOTA COROLLA"), any(LocalDateTime.class), any(LocalDateTime.class));
        verify(tariffStrategy, times(1)).calculateCost(any(LocalDateTime.class), any(LocalDateTime.class), eq(5000.0));

        // Verificar que se disparó el evento Observer
        verify(eventPublisher, times(1)).publishEvent(any(VehicleExitEvent.class));
    }
}
