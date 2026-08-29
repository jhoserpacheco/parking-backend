package com.nelumbo.parking.service.strategy;

import com.nelumbo.parking.service.strategy.impl.DayRateTariffStrategy;
import com.nelumbo.parking.service.strategy.impl.MotorcycleTariffStrategy;
import com.nelumbo.parking.service.strategy.impl.StandardHourlyTariffStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Patrón Factory Method / Strategy Factory.
 * Centraliza la selección y resolución de la estrategia tarifaria adecuada según el contexto.
 */
@Component
public class TariffStrategyFactory {

    private final Map<String, ParkingTariffStrategy> strategies;
    private final StandardHourlyTariffStrategy defaultStrategy;

    public TariffStrategyFactory(List<ParkingTariffStrategy> strategyList, StandardHourlyTariffStrategy defaultStrategy) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(ParkingTariffStrategy::getStrategyType, Function.identity()));
        this.defaultStrategy = defaultStrategy;
    }

    /**
     * Resuelve la estrategia adecuada según el tipo de vehículo y la duración de la estadía.
     *
     * @param vehicleModel Modelo o tipo de vehículo (ej: "MOTO", "SEDAN", etc.)
     * @param entryDate Fecha de ingreso
     * @param exitDate Fecha de salida
     * @return Implementación de ParkingTariffStrategy correspondiente
     */
    public ParkingTariffStrategy getStrategy(String vehicleModel, LocalDateTime entryDate, LocalDateTime exitDate) {
        if (vehicleModel != null && vehicleModel.toUpperCase().contains("MOTO")) {
            return strategies.getOrDefault(MotorcycleTariffStrategy.STRATEGY_TYPE, defaultStrategy);
        }

        if (entryDate != null && exitDate != null) {
            long hours = Duration.between(entryDate, exitDate).toHours();
            if (hours >= 8) {
                return strategies.getOrDefault(DayRateTariffStrategy.STRATEGY_TYPE, defaultStrategy);
            }
        }

        return defaultStrategy;
    }

    public ParkingTariffStrategy getStrategyByType(String strategyType) {
        return Optional.ofNullable(strategies.get(strategyType)).orElse(defaultStrategy);
    }
}
