/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.algonquincollege.simran_final_project.transferobject;

/**
 * DTO for a vehicle's internal system component (engine, battery, etc.)
 * Used for tracking wear-and-tear, runtime, and operational status.
 * 
 * Table mapped: system_components
 */
public class SystemComponentDTO {
    /** Unique component ID */
    private int id;

    /** Reference to the vehicle this component belongs to */
    private String vehicleRef;

    /** Component type (e.g., Engine, Battery, Brakes) */
    private String componentType;

    /** Total hours or KM the component has been in use */
    private double totalRuntime;

    /** Percentage of wear (0 to 100%) */
    private double usageWearPercent;

    /** Status indicator (e.g., Normal, Warning, Critical) */
    private String statusCheck;

    /**
     * Default constructor.
     */
    public SystemComponentDTO() {}

    /**
     * Full constructor for this DTO.
     * 
     * @param id unique ID
     * @param vehicleRef vehicle ID
     * @param componentType type of system
     * @param totalRuntime total hours/KM used
     * @param usageWearPercent wear percentage
     * @param statusCheck current system status
     */
    public SystemComponentDTO(int id, String vehicleRef, String componentType, double totalRuntime, double usageWearPercent, String statusCheck) {
        this.id = id;
        this.vehicleRef = vehicleRef;
        this.componentType = componentType;
        this.totalRuntime = totalRuntime;
        this.usageWearPercent = usageWearPercent;
        this.statusCheck = statusCheck;
    }

    /** @return component ID */
    public int getId() { return id; }

    /** @param id component ID */
    public void setId(int id) { this.id = id; }

    /** @return vehicle reference ID */
    public String getVehicleRef() { return vehicleRef; }

    /** @param vehicleRef vehicle ID */
    public void setVehicleRef(String vehicleRef) { this.vehicleRef = vehicleRef; }

    /** @return type of component */
    public String getComponentType() { return componentType; }

    /** @param componentType e.g. Engine, Battery */
    public void setComponentType(String componentType) { this.componentType = componentType; }

    /** @return total runtime in hours or KM */
    public double getTotalRuntime() { return totalRuntime; }

    /** @param totalRuntime runtime of the component */
    public void setTotalRuntime(double totalRuntime) { this.totalRuntime = totalRuntime; }

    /** @return wear in percentage */
    public double getUsageWearPercent() { return usageWearPercent; }

    /** @param usageWearPercent how much it's worn out */
    public void setUsageWearPercent(double usageWearPercent) { this.usageWearPercent = usageWearPercent; }

    /** @return component status check result */
    public String getStatusCheck() { return statusCheck; }

    /** @param statusCheck current condition label */
    public void setStatusCheck(String statusCheck) { this.statusCheck = statusCheck; }
}
