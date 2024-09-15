package com.nelumbo.parking.service;

import com.nelumbo.parking.dto.StatsParkingDto;
import com.nelumbo.parking.dto.StatsUserDto;
import com.nelumbo.parking.dto.VehicleDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface IStatsService {
    List<VehicleDto> Top10MostRegisteredVehicles();
    List<VehicleDto> Top10MostRegisteredByParkingId(UUID parkingId);
    List<VehicleDto> getAllVehiclesRegisteredForTheFirstTime();
    Double getEarningsForToday(UUID parkingId);
    Double getEarningsForWeek(UUID parkingId);
    Double getEarningsForMonth(UUID parkingId);
    Double getEarningsForYear(UUID parkingId);
    List<StatsUserDto> top3SociosMostRegisteredCurrentWeek();
    List<StatsParkingDto> top3ParkingsWithHighestEarningsThisWeek();
}
