package com.nelumbo.parking.service.strategy;

import com.nelumbo.parking.service.strategy.impl.DayRateTariffStrategy;
import com.nelumbo.parking.service.strategy.impl.MotorcycleTariffStrategy;
import com.nelumbo.parking.service.strategy.impl.StandardHourlyTariffStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TariffStrategyFactoryTest {

    private TariffStrategyFactory factory;
    private StandardHourlyTariffStrategy standardStrategy;
    private DayRateTariffStrategy dayRateStrategy;
    private MotorcycleTariffStrategy motorcycleStrategy;

    @BeforeEach
    void setUp() {
        standardStrategy = new StandardHourlyTariffStrategy();
        dayRateStrategy = new DayRateTariffStrategy();
        motorcycleStrategy = new MotorcycleTariffStrategy();

        factory = new TariffStrategyFactory(
                List.of(standardStrategy, dayRateStrategy, motorcycleStrategy),
                standardStrategy
        );
    }

    @Test
    @DisplayName("Debe resolver MotorcycleTariffStrategy cuando el modelo es motocicleta")
    void getStrategy_MotorcycleModel_ReturnsMotorcycleStrategy() {
        LocalDateTime now = LocalDateTime.now();
        ParkingTariffStrategy strategy = factory.getStrategy("MOTO YAMAHA", now, now.plusHours(2));

        assertInstanceOf(MotorcycleTariffStrategy.class, strategy);
    }

    @Test
    @DisplayName("Debe resolver DayRateTariffStrategy cuando la estadía es superior o igual a 8 horas")
    void getStrategy_LongDuration_ReturnsDayRateStrategy() {
        LocalDateTime entry = LocalDateTime.of(2026, 8, 27, 8, 0);
        LocalDateTime exit = LocalDateTime.of(2026, 8, 27, 18, 0); // 10 horas

        ParkingTariffStrategy strategy = factory.getStrategy("AUTOMOVIL", entry, exit);

        assertInstanceOf(DayRateTariffStrategy.class, strategy);
    }

    @Test
    @DisplayName("Debe resolver StandardHourlyTariffStrategy por defecto para autos con estadía regular")
    void getStrategy_RegularStay_ReturnsStandardHourlyStrategy() {
        LocalDateTime entry = LocalDateTime.of(2026, 8, 27, 8, 0);
        LocalDateTime exit = LocalDateTime.of(2026, 8, 27, 10, 0); // 2 horas

        ParkingTariffStrategy strategy = factory.getStrategy("AUTOMOVIL CHEVROLET", entry, exit);

        assertInstanceOf(StandardHourlyTariffStrategy.class, strategy);
    }
}
