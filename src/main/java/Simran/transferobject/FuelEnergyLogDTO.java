/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Simran.transferobject;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for the fuel_energy_logs table.
 * Used to pass data between DAO, Business Logic, and Presentation layers.
 * @author 
 */
public class FuelEnergyLogDTO {
    private int id;
    private String vehicleRef;
    private String energyType;
    private double quantityUsed;
    private double kmCovered;
    private LocalDateTime recordedAt;

    public FuelEnergyLogDTO() {}

    public FuelEnergyLogDTO(int id, String vehicleRef, String energyType, double quantityUsed, double kmCovered, LocalDateTime recordedAt) {
        this.id = id;
        this.vehicleRef = vehicleRef;
        this.energyType = energyType;
        this.quantityUsed = quantityUsed;
        this.kmCovered = kmCovered;
        this.recordedAt = recordedAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getVehicleRef() { return vehicleRef; }
    public void setVehicleRef(String vehicleRef) { this.vehicleRef = vehicleRef; }

    public String getEnergyType() { return energyType; }
    public void setEnergyType(String energyType) { this.energyType = energyType; }

    public double getQuantityUsed() { return quantityUsed; }
    public void setQuantityUsed(double quantityUsed) { this.quantityUsed = quantityUsed; }

    public double getKmCovered() { return kmCovered; }
    public void setKmCovered(double kmCovered) { this.kmCovered = kmCovered; }

    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }
}
