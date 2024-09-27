package com.nelumbo.parking.seeder;


import com.nelumbo.parking.entity.Parking;
import com.nelumbo.parking.entity.ParkingHistory;
import com.nelumbo.parking.entity.Vehicle;
import com.nelumbo.parking.repository.IParkingHistoryRepository;
import com.nelumbo.parking.repository.IParkingRepository;
import com.nelumbo.parking.repository.IVehicleRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ParkingSeeder implements CommandLineRunner {

    private final IParkingHistoryRepository parkingHistoryRepository;
    private final IParkingRepository parkingRepository;
    private final IVehicleRepository vehicleRepository;
    private final List<String> socioList = List.of("socio1@gmail.com", "socio2@gmail.com");
    private final Faker faker = new Faker();

    private void loadParkingData(){
        if (parkingRepository.count() != 0){
            System.out.println("Parking data already loaded.");
            return;
        }
        for (int i = 0; i < 15; i++) {
            Parking parking = new Parking();
            parking.setId(UUID.randomUUID());
            parking.setName(faker.company().name() + " Parking");
            parking.setMaxCapacity(faker.number().numberBetween(5, 20));
            parking.setCurrentCapacity(faker.number().numberBetween(0, parking.getMaxCapacity()));
            parking.setCostHour(faker.number().randomDouble(0, 500, 2000));
            parking.setDirection(faker.address().streetAddress());
            parking.setEmailUser(socioList.get(faker.number().numberBetween(0, 1)));
            parking = parkingRepository.save(parking);

            // Generar datos para Vehicles en ese Parking
            for (int j = 0; j < parking.getMaxCapacity(); j++) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(UUID.randomUUID());
                vehicle.setVehiclePlate(faker.regexify("^[a-zA-Z0-9]{6}$"));
                vehicle.setTotalVisit(faker.number().numberBetween(1, 100));
                vehicle.setModel(faker.vehicle().model());
                vehicle.setParking(parking);
                vehicleRepository.save(vehicle);

                // Generar datos para ParkingHistory
                ParkingHistory history = new ParkingHistory();
                history.setId(UUID.randomUUID());
                history.setVehicle(vehicle);
                history.setParking(parking);
                history.setEntryDate(randomDateBetween());
                // 20% de los vehículos no tendrán salida registrada
                if (faker.number().numberBetween(1, 5) != 1) {
                    history.setExitDate(randomDateAfter(history.getEntryDate()));
                    history.setTotalCost(calculateCost(parking.getCostHour(), history.getEntryDate(), history.getExitDate()));
                }
                parkingHistoryRepository.save(history);
            }
        }
    }
    private LocalDateTime randomDateBetween() {
        return LocalDateTime.now().minusDays(faker.number().numberBetween(1, 30));
    }

    private LocalDateTime randomDateAfter(LocalDateTime entryDate) {
        return entryDate.plusHours(faker.number().numberBetween(1, 72));
    }

    private double calculateCost(double costHour, LocalDateTime entryDate, LocalDateTime exitDate) {
        long hours = java.time.Duration.between(entryDate, exitDate).toHours();
        return hours * costHour;
    }

    @Override
    public void run(String... args) throws Exception {
        loadParkingData();
    }
}
