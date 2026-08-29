package com.nelumbo.parking.service.strategy.impl;

import com.nelumbo.parking.service.strategy.ParkingTariffStrategy;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Estrategia de cobro estándar por hora o fracción de hora iniciada.
 * Resuelve el fallo aritmético previo aplicando redondeo al techo superior (Ceiling).
 */
@Component("standardHourlyTariffStrategy")
public class StandardHourlyTariffStrategy implements ParkingTariffStrategy {

    public static final String STRATEGY_TYPE = "STANDARD_HOURLY";

    @Override
    public double calculateCost(LocalDateTime entryDate, LocalDateTime exitDate, double costHour) {
        if (entryDate == null || exitDate == null || exitDate.isBefore(entryDate)) {
            throw new IllegalArgumentException("Las fechas de entrada y salida son inválidas");
        }

        long totalMinutes = Duration.between(entryDate, exitDate).toMinutes();

        // Si la estancia es de 0 minutos o menos, se cobra la fracción mínima (1 hora)
        if (totalMinutes <= 0) {
            return costHour;
        }

        // Cobro por hora iniciada: 1 a 60 min -> 1 hora, 61 a 120 min -> 2 horas
        long billableHours = (totalMinutes + 59) / 60;
        return costHour * billableHours;
    }

    @Override
    public String getStrategyType() {
        return STRATEGY_TYPE;
    }
}
