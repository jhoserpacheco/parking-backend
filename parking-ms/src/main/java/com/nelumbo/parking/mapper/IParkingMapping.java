package com.nelumbo.parking.mapper;

import com.nelumbo.parking.dto.ParkingDto;
import com.nelumbo.parking.entity.Parking;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IParkingMapping {
    IParkingMapping INSTANCE = Mappers.getMapper( IParkingMapping.class );

    ParkingDto parkingToParkingDto(Parking parking);
    Parking parkingDtoToParking(ParkingDto parkingDto);

    List<ParkingDto> parkingListToParkingDtoList(List<Parking> parkingList);
    List<Parking> parkingDtoListToParkingList(List<ParkingDto> parkingDtoList);
}
