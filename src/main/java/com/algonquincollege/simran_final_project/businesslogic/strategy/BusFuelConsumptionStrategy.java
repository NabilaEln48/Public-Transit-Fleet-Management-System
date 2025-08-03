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
public class BusFuelConsumptionStrategy implements IConsumptionStrategy {
    private static final double ALERT_THRESHOLD = 2.5; // km/L

    @Override
    public double calculateEfficiency(FuelEnergyLogDTO log) {
        return log.getKmCovered() / log.getQuantityUsed();
    }

    @Override
    public double getAlertThreshold() {
        return ALERT_THRESHOLD;
    }
}
