package com.nelumbo.parking.mapper;

import com.nelumbo.parking.dto.ParkingHistoryDto;
import com.nelumbo.parking.entity.ParkingHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IParkingHistoryMapping {
    IParkingHistoryMapping INSTANCE = Mappers.getMapper(IParkingHistoryMapping.class);

    @Mapping(source = "parking.id", target = "idParking")
    @Mapping(source = "vehicle.vehiclePlate", target = "vehiclePlate")
    ParkingHistoryDto parkingHistoryToParkingHistoryDto(ParkingHistory parkingHistory);

    @Mapping(source = "idParking", target = "parking.id")
    @Mapping(source = "vehiclePlate", target = "vehicle.vehiclePlate")
    ParkingHistory parkingHistoryDtoToParkingHistory(ParkingHistoryDto parkingHistoryDto);

}
