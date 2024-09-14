package com.nelumbo.parking.mapper;

import com.nelumbo.parking.dto.VehicleDto;
import com.nelumbo.parking.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IVehicleMapping {

    IVehicleMapping INSTANCE = Mappers.getMapper( IVehicleMapping.class );

    @Mapping(source = "parking.id", target = "idParking")
    VehicleDto vehicleToVehicleDto(Vehicle vehicle);

    @Mapping(source = "idParking", target = "parking.id")
    Vehicle vehicleDtoToVehicle(VehicleDto vehicleDto);

    List<VehicleDto> toDtoList(List<Vehicle> vehicle);

    List<Vehicle> toEntityList(List<VehicleDto> vehicleDto);

}
