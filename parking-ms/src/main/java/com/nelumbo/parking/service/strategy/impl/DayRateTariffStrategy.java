package com.nelumbo.parking.service.strategy.impl;

import com.nelumbo.parking.service.strategy.ParkingTariffStrategy;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Estrategia de cobro con tarifa plana diaria para estancias prolongadas (>8 horas o días completos).
 * Aplica un tope diario equivalente a 8 horas de tarifa estándar por cada bloque de 24 horas.
 */
@Component("dayRateTariffStrategy")
public class DayRateTariffStrategy implements ParkingTariffStrategy {

    public static final String STRATEGY_TYPE = "DAY_RATE";
    private static final int MAX_HOURLY_CAP_PER_DAY = 8;

    @Override
    public double calculateCost(LocalDateTime entryDate, LocalDateTime exitDate, double costHour) {
        if (entryDate == null || exitDate == null || exitDate.isBefore(entryDate)) {
            throw new IllegalArgumentException("Las fechas de entrada y salida son inválidas");
        }

        long totalMinutes = Duration.between(entryDate, exitDate).toMinutes();
        if (totalMinutes <= 0) {
            return costHour;
        }

        long totalHours = (totalMinutes + 59) / 60;
        long fullDays = totalHours / 24;
        long remainingHours = totalHours % 24;

        // Por cada día completo, se cobra el tope de 8 horas
        double totalCost = fullDays * (costHour * MAX_HOURLY_CAP_PER_DAY);

        // Para las horas restantes del último día parcial:
        if (remainingHours > MAX_HOURLY_CAP_PER_DAY) {
            totalCost += (costHour * MAX_HOURLY_CAP_PER_DAY);
        } else {
            totalCost += (costHour * remainingHours);
        }

        return totalCost;
    }

    @Override
    public String getStrategyType() {
        return STRATEGY_TYPE;
    }
}
