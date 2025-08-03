/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.algonquincollege.simran_final_project.businesslogic.strategy;

import com.algonquincollege.simran_final_project.transferobject.FuelEnergyLogDTO;

/**
 *
 * @author simra
 */
public class ConsumptionContext {
    private IConsumptionStrategy strategy;

    public void setStrategy(IConsumptionStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Calculates efficiency using the assigned strategy.
     *
     * @param log DTO of fuel/energy log
     * @return efficiency value
     */
    public double executeStrategy(FuelEnergyLogDTO log) {
        return strategy.calculateEfficiency(log);
    }

    /**
     * Returns the threshold defined in the strategy.
     * @return threshold value.
     */
    public double getThreshold() {
        return strategy.getAlertThreshold();
    }
}
