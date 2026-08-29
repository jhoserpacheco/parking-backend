package com.nelumbo.parking.service.strategy.impl;

import com.nelumbo.parking.service.strategy.ParkingTariffStrategy;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Estrategia de cobro diferenciada para motocicletas.
 * Aplica un factor de reducción del 50% sobre la tarifa horaria estándar.
 */
@Component("motorcycleTariffStrategy")
public class MotorcycleTariffStrategy implements ParkingTariffStrategy {

    public static final String STRATEGY_TYPE = "MOTORCYCLE";
    private static final double MOTORCYCLE_DISCOUNT_FACTOR = 0.50; // 50% de descuento

    @Override
    public double calculateCost(LocalDateTime entryDate, LocalDateTime exitDate, double costHour) {
        if (entryDate == null || exitDate == null || exitDate.isBefore(entryDate)) {
            throw new IllegalArgumentException("Las fechas de entrada y salida son inválidas");
        }

        long totalMinutes = Duration.between(entryDate, exitDate).toMinutes();
        if (totalMinutes <= 0) {
            return costHour * MOTORCYCLE_DISCOUNT_FACTOR;
        }

        long billableHours = (totalMinutes + 59) / 60;
        return (costHour * MOTORCYCLE_DISCOUNT_FACTOR) * billableHours;
    }

    @Override
    public String getStrategyType() {
        return STRATEGY_TYPE;
    }
}
