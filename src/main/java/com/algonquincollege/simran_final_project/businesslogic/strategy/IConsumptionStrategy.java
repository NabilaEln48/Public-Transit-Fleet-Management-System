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
public interface IConsumptionStrategy {
    /**
     * Calculates the efficiency of a given fuel or energy log.
     *
     * @param log The DTO containing log data.
     * @return calculated efficiency value.
     */
    double calculateEfficiency(FuelEnergyLogDTO log);

    /**
     * Gets the threshold value below which alerts are raised.
     *
     * @return efficiency threshold.
     */
    double getAlertThreshold();
}
