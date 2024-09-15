package com.nelumbo.parking.service.impl;

import com.nelumbo.parking.dto.StatsParkingDto;
import com.nelumbo.parking.dto.StatsUserDto;
import com.nelumbo.parking.dto.VehicleDto;
import com.nelumbo.parking.entity.ParkingHistory;
import com.nelumbo.parking.entity.Vehicle;
import com.nelumbo.parking.mapper.IVehicleMapping;
import com.nelumbo.parking.repository.IParkingHistoryRepository;
import com.nelumbo.parking.repository.IVehicleRepository;
import com.nelumbo.parking.service.IStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements IStatsService {

    private final IParkingHistoryRepository parkingHistoryRepository;
    private final IVehicleRepository vehicleRepository;
    private final ParkingServiceImpl parkingService;
    private final IVehicleMapping vehicleMapping = IVehicleMapping.INSTANCE;

    //admin y socio
    // top10 vehiculos mas registrados en los parqueaderos
    public List<VehicleDto> Top10MostRegisteredVehicles(){
        return vehicleMapping.toDtoList(vehicleRepository.findTop10ByOrderByTotalVisitDesc());
    }

    // top10 vehiculos que se han regisrtado en x parqueadero y cuantas veces
    public List<VehicleDto> Top10MostRegisteredByParkingId(UUID parkingId){
        List<ParkingHistory> parkingHistoryList = parkingHistoryRepository.findTop10ByParkingIdOrderByVehicleTotalVisitDesc(
                parkingId);
        return getVehiclesFromParkingHistory(parkingHistoryList);
    }

    // verificar en cada parqueadero cual esta por 1ra vez
    public List<VehicleDto> getAllVehiclesRegisteredForTheFirstTime(){
        int totalVisit = 1;
        List<ParkingHistory> parkingHistoryList = parkingHistoryRepository
                .findAllByVehicleTotalVisitOrderByVehicleTotalVisitDesc(totalVisit);
        return getVehiclesFromParkingHistory(parkingHistoryList);
    }

    // socio
    // ganancias de hoy, semana, mes, año de x parquadero
    public Double getEarningsForToday(UUID parkingId) {
        parkingService.isParkingSocioAsociated(parkingId);
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        return parkingHistoryRepository.getTotalEarnings(parkingId, startOfDay, endOfDay);
    }

    public Double getEarningsForWeek(UUID parkingId) {
        parkingService.isParkingSocioAsociated(parkingId);
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime start = startOfWeek.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);
        return parkingHistoryRepository.getTotalEarnings(parkingId, start, end);
    }

    public Double getEarningsForMonth(UUID parkingId) {
        parkingService.isParkingSocioAsociated(parkingId);
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime start = startOfMonth.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);
        return parkingHistoryRepository.getTotalEarnings(parkingId, start, end);
    }

    public Double getEarningsForYear(UUID parkingId) {
        parkingService.isParkingSocioAsociated(parkingId);
        LocalDate today = LocalDate.now();
        LocalDate startOfYear = today.with(TemporalAdjusters.firstDayOfYear());
        LocalDateTime start = startOfYear.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);
        return parkingHistoryRepository.getTotalEarnings(parkingId, start, end);
    }


    //admin
    // top 3 socios con mas ingresos de vehiculos en la semana actual y mostrar cantidad vehiculos
    public List<StatsUserDto> top3SociosMostRegisteredCurrentWeek(){
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime start = startOfWeek.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

        List<StatsUserDto> users = convertObjectListToStatsUser(parkingHistoryRepository
                .getTop3UsersWithMostVehicles(start, end, Pageable.ofSize(3)));

        return users;
    }

    // top 3 de parqueaderos con mayor ganancia
    public List<StatsParkingDto> top3ParkingsWithHighestEarningsThisWeek(){
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime start = startOfWeek.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

        List<StatsParkingDto> parking = convertObjectListToStatsParking(parkingHistoryRepository
                .getTop3ParkingsWithHighestEarnings(start, end, Pageable.ofSize(3)));

        return parking;
    }

    private List<VehicleDto> getVehiclesFromParkingHistory(List<ParkingHistory> parkingHistory){
        List<Vehicle> vehicles = new ArrayList<>();
        for(ParkingHistory parkingHistoryItem : parkingHistory){
            vehicles.add(parkingHistoryItem.getVehicle());
        }
        return vehicleMapping.toDtoList(vehicles);
    }

    private List<StatsUserDto> convertObjectListToStatsUser(List<Object[]> objects){
        List<StatsUserDto> user = objects.stream()
                .map(obj -> new StatsUserDto((String) obj[0], (Long) obj[1]))
                .toList();
        return user;
    }

    private List<StatsParkingDto> convertObjectListToStatsParking(List<Object[]> objects){
        List<StatsParkingDto> parking = objects.stream()
                .map(obj -> new StatsParkingDto(UUID.fromString(obj[0].toString()), (String) obj[1],(String) obj[2], (Double) obj[3]))
                .toList();
        return parking;
    }


}
