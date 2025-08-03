/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.algonquincollege.simran_final_project.transferobject;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for fuel and energy consumption logs.
 * Represents one energy usage event (e.g., fuel or electricity).
 * Used by DAO and service layers for clean separation of concerns.
 * 
 * Table mapped: fuel_energy_logs
 */
public class FuelEnergyLogDTO {
    /** Unique identifier for this fuel/energy usage entry */
    private int id;

    /** Foreign key referencing vehicle's ID */
    private String vehicleRef;

    /** Type of energy used (e.g., Gasoline, Diesel, Electric) */
    private String energyType;

    /** Quantity of energy used in litres or kWh */
    private double quantityUsed;

    /** Distance covered in kilometers */
    private double kmCovered;

    /** Timestamp when the entry was recorded */
    private LocalDateTime recordedAt;

    /**
     * Default constructor required for frameworks or reflection.
     */
    public FuelEnergyLogDTO() {}

    /**
     * Full constructor to create a complete DTO.
     * 
     * @param id Unique identifier
     * @param vehicleRef Vehicle reference ID
     * @param energyType Type of fuel or energy
     * @param quantityUsed Quantity consumed
     * @param kmCovered Distance driven
     * @param recordedAt Timestamp of the entry
     */
    public FuelEnergyLogDTO(int id, String vehicleRef, String energyType, double quantityUsed, double kmCovered, LocalDateTime recordedAt) {
        this.id = id;
        this.vehicleRef = vehicleRef;
        this.energyType = energyType;
        this.quantityUsed = quantityUsed;
        this.kmCovered = kmCovered;
        this.recordedAt = recordedAt;
    }

    // Getters and setters with Javadoc for all properties

    /** @return unique log entry ID */
    public int getId() { return id; }

    /** @param id log entry ID */
    public void setId(int id) { this.id = id; }

    /** @return reference to the associated vehicle */
    public String getVehicleRef() { return vehicleRef; }

    /** @param vehicleRef vehicle reference string */
    public void setVehicleRef(String vehicleRef) { this.vehicleRef = vehicleRef; }

    /** @return energy type used */
    public String getEnergyType() { return energyType; }

    /** @param energyType fuel or energy type */
    public void setEnergyType(String energyType) { this.energyType = energyType; }

    /** @return quantity used in litres/kWh */
    public double getQuantityUsed() { return quantityUsed; }

    /** @param quantityUsed fuel/energy consumed */
    public void setQuantityUsed(double quantityUsed) { this.quantityUsed = quantityUsed; }

    /** @return distance driven in kilometers */
    public double getKmCovered() { return kmCovered; }

    /** @param kmCovered kilometers travelled */
    public void setKmCovered(double kmCovered) { this.kmCovered = kmCovered; }

    /** @return date/time this record was entered */
    public LocalDateTime getRecordedAt() { return recordedAt; }

    /** @param recordedAt date and time of log */
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }
}
