package com.nelumbo.parking.feign;

import com.nelumbo.parking.dto.VehicleDto;
import com.nelumbo.parking.exceptions.VehicleNotFoundException;
import com.nelumbo.parking.service.impl.VehicleServiceImpl;
import com.nelumbo.parking.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailClientImpl {

    private final VehicleServiceImpl vehicleService;
    private final EmailClient emailClient;

    public ResponseEmailDto sendEmailIfVehicleExists(String token, EmailParkingDto emailParkingDto) {
        Optional<VehicleDto> vehicle = vehicleService.findByVehiclePlate(emailParkingDto.getVehiclePlate());
        if (vehicle.isPresent()) {
            return emailClient.sendEmail(token, emailParkingDto);
        } else {
            throw new VehicleNotFoundException(Constants.Message.VEHICLE_NOT_FOUND);
        }
    }
}
