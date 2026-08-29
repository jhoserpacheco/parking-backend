package com.nelumbo.parking.service.strategy;

import java.time.LocalDateTime;

/**
 * Interfaz que define la estrategia para el cálculo tarifario de estadía en parqueaderos.
 * Aplica el Patrón GoF Strategy para desacoplar el algoritmo de cobro del flujo transaccional.
 */
public interface ParkingTariffStrategy {

    /**
     * Calcula el costo total del parqueadero según la duración de la estadía y la tarifa base por hora.
     *
     * @param entryDate Fecha y hora de ingreso del vehículo
     * @param exitDate Fecha y hora de salida del vehículo
     * @param costHour Tarifa base por hora configurada en el parqueadero
     * @return Costo liquidado total
     */
    double calculateCost(LocalDateTime entryDate, LocalDateTime exitDate, double costHour);

    /**
     * Identificador del tipo de estrategia tarifaria.
     *
     * @return Tipo de estrategia
     */
    String getStrategyType();
}
