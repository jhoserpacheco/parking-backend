package com.nelumbo.parking.service.strategy;

import com.nelumbo.parking.service.strategy.impl.MotorcycleTariffStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MotorcycleTariffStrategyTest {

    private MotorcycleTariffStrategy strategy;
    private static final double HOURLY_RATE = 4000.0;

    @BeforeEach
    void setUp() {
        strategy = new MotorcycleTariffStrategy();
    }

    @Test
    @DisplayName("Debe aplicar 50% de descuento sobre tarifa base para motos")
    void calculateCost_MotorcycleDiscount() {
        LocalDateTime entry = LocalDateTime.of(2026, 8, 27, 10, 0);
        LocalDateTime exit = LocalDateTime.of(2026, 8, 27, 12, 0); // 2 horas

        double cost = strategy.calculateCost(entry, exit, HOURLY_RATE);

        // Tarifa base $4000 * 0.5 = $2000 por hora * 2 horas = $4000
        assertEquals(4000.0, cost);
    }
}
