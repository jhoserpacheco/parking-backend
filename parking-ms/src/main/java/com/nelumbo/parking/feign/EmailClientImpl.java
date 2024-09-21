package com.nelumbo.parking.feign;

import com.nelumbo.parking.dto.VehicleDto;
import com.nelumbo.parking.mgsbroker.RabbitQueueSender;
import com.nelumbo.parking.service.impl.VehicleServiceImpl;
import com.nelumbo.parking.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailClientImpl {

    private final VehicleServiceImpl vehicleService;
    private final RabbitQueueSender queueSender;

    public ResponseEmailDto sendEmailIfVehicleExists(EmailParkingDto emailParkingDto) {
        Optional<VehicleDto> vehicle = vehicleService.findByVehiclePlate(emailParkingDto.getVehiclePlate());
        if (vehicle.isPresent()) {
            emailParkingDto.setId(UUID.randomUUID());
            queueSender.send(emailParkingDto);
            return new ResponseEmailDto(emailParkingDto.getId(), Constants.EmailTemplate.StatusEmail.PROCESSING.name());
        } else {
            throw new RuntimeException(Constants.Message.VEHICLE_NOT_FOUND);
        }
    }
}
