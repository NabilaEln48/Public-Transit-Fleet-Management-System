/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Prabhsimran Kaur
 * Student Id: 041119310
 * Professor Name: Teddy Yap
 */

package Simran.transferobject;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for the fuel_energy_logs table.
 * 
 * Used to pass fuel/energy log data between DAO, Business Logic,
 * and Presentation layers.
 * 
 * Contains log details such as vehicle reference, energy type,
 * quantity used, kilometers covered, and recorded time.
 * 
 * Author: Prabhsimran Kaur
 */
public class FuelEnergyLogDTO {

    /** Unique identifier of the log entry. */
    private int id;

    /** Reference ID of the vehicle associated with the log. */
    private String vehicleRef;

    /** Type of energy used (e.g., Diesel, Electric). */
    private String energyType;

    /** Amount of fuel/energy consumed. */
    private double quantityUsed;

    /** Distance covered in kilometers. */
    private double kmCovered;

    /** Timestamp when the log was recorded. */
    private LocalDateTime recordedAt;

    /**
     * Default no-argument constructor.
     */
    public FuelEnergyLogDTO() {}

    /**
     * Parameterized constructor to initialize all fields.
     *
     * @param id           the log ID
     * @param vehicleRef   the vehicle reference
     * @param energyType   the type of energy used
     * @param quantityUsed the quantity of energy consumed
     * @param kmCovered    the distance covered
     * @param recordedAt   the time the log was recorded
     */
    public FuelEnergyLogDTO(int id, String vehicleRef, String energyType, double quantityUsed, double kmCovered, LocalDateTime recordedAt) {
        this.id = id;
        this.vehicleRef = vehicleRef;
        this.energyType = energyType;
        this.quantityUsed = quantityUsed;
        this.kmCovered = kmCovered;
        this.recordedAt = recordedAt;
    }

    /** @return the log ID */
    public int getId() { return id; }

    /** @param id the log ID to set */
    public void setId(int id) { this.id = id; }

    /** @return the vehicle reference */
    public String getVehicleRef() { return vehicleRef; }

    /** @param vehicleRef the vehicle reference to set */
    public void setVehicleRef(String vehicleRef) { this.vehicleRef = vehicleRef; }

    /** @return the energy type */
    public String getEnergyType() { return energyType; }

    /** @param energyType the energy type to set */
    public void setEnergyType(String energyType) { this.energyType = energyType; }

    /** @return the quantity used */
    public double getQuantityUsed() { return quantityUsed; }

    /** @param quantityUsed the quantity used to set */
    public void setQuantityUsed(double quantityUsed) { this.quantityUsed = quantityUsed; }

    /** @return the kilometers covered */
    public double getKmCovered() { return kmCovered; }

    /** @param kmCovered the kilometers covered to set */
    public void setKmCovered(double kmCovered) { this.kmCovered = kmCovered; }

    /** @return the recorded timestamp */
    public LocalDateTime getRecordedAt() { return recordedAt; }

    /** @param recordedAt the recorded timestamp to set */
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }
}