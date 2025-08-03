/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transferObject;

/**
 *
 * @author simra
 */
public class FuelUsageDTO {
    private int usageId;
    private String vehicleId;
    private String energyType;
    private double quantityUsed;
    private double distanceTraveled;
    private String timestamp;

    /**
     * Gets the usage ID.
     * @return the usage ID
     */
    public int getUsageId() {
        return usageId;
    }

    /**
     * Sets the usage ID.
     * @param usageId the usage ID
     */
    public void setUsageId(int usageId) {
        this.usageId = usageId;
    }

    /**
     * Gets the vehicle ID.
     * @return the vehicle ID
     */
    public String getVehicleId() {
        return vehicleId;
    }

    /**
     * Sets the vehicle ID.
     * @param vehicleId the vehicle ID
     */
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * Gets the fuel/energy type.
     * @return the fuel/energy type
     */
    public String getEnergyType() {
        return energyType;
    }

    /**
     * Sets the fuel/energy type.
     * @param energyType the fuel/energy type
     */
    public void setEnergyType(String energyType) {
        this.energyType = energyType;
    }

    /**
     * Gets the amount used.
     * @return the amount used
     */
    public double getQuantityUsed() {
        return quantityUsed;
    }

    /**
     * Sets the amount used.
     * @param quantityUsed the amount used
     */
    public void setQuantityUsed(double quantityUsed) {
        this.quantityUsed = quantityUsed;
    }

    /**
     * Gets the distance traveled.
     * @return the distance traveled
     */
    public double getDistanceTraveled() {
        return distanceTraveled;
    }

    /**
     * Sets the distance traveled.
     * @param distanceTraveled the distance traveled
     */
    public void setDistanceTraveled(double distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }

    /**
     * Gets the timestamp.
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp.
     * @param timestamp the timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
}
