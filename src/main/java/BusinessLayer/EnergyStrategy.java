/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLayer;

/**
 * Strategy interface for energy efficiency calculations.
 * @author Pra
 */
public interface EnergyStrategy {
    /**
     * Checks if energy usage is efficient.
     * @param quantityUsed the amount used
     * @param distanceTraveled the distance traveled
     * @return true if efficient, false otherwise
     */
    boolean isEfficient(double quantityUsed, double distanceTraveled);
}
