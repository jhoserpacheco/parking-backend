package com.nelumbo.parking.service.strategy;

import com.nelumbo.parking.service.strategy.impl.DayRateTariffStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DayRateTariffStrategyTest {

    private DayRateTariffStrategy strategy;
    private static final double HOURLY_RATE = 5000.0;

    @BeforeEach
    void setUp() {
        strategy = new DayRateTariffStrategy();
    }

    @Test
    @DisplayName("Estancia corta (< 8 horas) debe cobrar por horas reales")
    void calculateCost_UnderCapHours() {
        LocalDateTime entry = LocalDateTime.of(2026, 8, 27, 8, 0);
        LocalDateTime exit = LocalDateTime.of(2026, 8, 27, 12, 0); // 4 horas

        double cost = strategy.calculateCost(entry, exit, HOURLY_RATE);

        assertEquals(20000.0, cost); // 4 * 5000
    }

    @Test
    @DisplayName("Estancia de 12 horas en un mismo día debe topar en 8 horas ($40000)")
    void calculateCost_TwelveHours_CapsAtEightHours() {
        LocalDateTime entry = LocalDateTime.of(2026, 8, 27, 8, 0);
        LocalDateTime exit = LocalDateTime.of(2026, 8, 27, 20, 0); // 12 horas

        double cost = strategy.calculateCost(entry, exit, HOURLY_RATE);

        assertEquals(40000.0, cost); // 8 * 5000
    }

    @Test
    @DisplayName("Estancia de 28 horas (1 día completo + 4 horas)")
    void calculateCost_TwentyEightHours() {
        LocalDateTime entry = LocalDateTime.of(2026, 8, 27, 8, 0);
        LocalDateTime exit = LocalDateTime.of(2026, 8, 28, 12, 0); // 28 horas

        double cost = strategy.calculateCost(entry, exit, HOURLY_RATE);

        // 1 día (8h * 5000 = 40000) + 4 horas (4 * 5000 = 20000) = 60000
        assertEquals(60000.0, cost);
    }
}
