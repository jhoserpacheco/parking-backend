package com.nelumbo.parking.service.strategy;

import com.nelumbo.parking.service.strategy.impl.StandardHourlyTariffStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StandardHourlyTariffStrategyTest {

    private StandardHourlyTariffStrategy strategy;
    private static final double HOURLY_RATE = 5000.0;

    @BeforeEach
    void setUp() {
        strategy = new StandardHourlyTariffStrategy();
    }

    @Test
    @DisplayName("Debe cobrar 1 hora mínima para estancias de 15 minutos (Corrección de Bug histórico)")
    void calculateCost_FifteenMinutesStay_ShouldChargeOneHour() {
        LocalDateTime entry = LocalDateTime.of(2026, 8, 27, 10, 0);
        LocalDateTime exit = LocalDateTime.of(2026, 8, 27, 10, 15);

        double cost = strategy.calculateCost(entry, exit, HOURLY_RATE);

        assertEquals(5000.0, cost, "15 minutos deben cobrarse como 1 hora completa ($5000)");
    }

    @ParameterizedTest(name = "Estadía de {0} minutos debe costar ${1}")
    @CsvSource({
            "0, 5000.0",
            "1, 5000.0",
            "30, 5000.0",
            "59, 5000.0",
            "60, 5000.0",
            "61, 10000.0",
            "119, 10000.0",
            "120, 10000.0",
            "121, 15000.0"
    })
    @DisplayName("Debe redondear al techo superior de horas iniciadas")
    void calculateCost_CeilingHourlyRounding(long minutes, double expectedCost) {
        LocalDateTime entry = LocalDateTime.of(2026, 8, 27, 10, 0);
        LocalDateTime exit = entry.plusMinutes(minutes);

        double cost = strategy.calculateCost(entry, exit, HOURLY_RATE);

        assertEquals(expectedCost, cost);
    }

    @Test
    @DisplayName("Debe lanzar excepción si fecha de salida es anterior a la de entrada")
    void calculateCost_ExitBeforeEntry_ThrowsException() {
        LocalDateTime entry = LocalDateTime.of(2026, 8, 27, 10, 0);
        LocalDateTime exit = LocalDateTime.of(2026, 8, 27, 9, 0);

        assertThrows(IllegalArgumentException.class, () -> strategy.calculateCost(entry, exit, HOURLY_RATE));
    }

    @Test
    @DisplayName("Debe retornar el tipo de estrategia correcto")
    void getStrategyType() {
        assertEquals("STANDARD_HOURLY", strategy.getStrategyType());
    }
}
