/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.algonquincollege.simran_final_project.transferobject;

import java.time.LocalDateTime;

/**
 * DTO for alert or fault records logged by the monitoring system.
 * Includes type, timestamp, and resolution state.
 * 
 * Table mapped: system_alerts
 */
public class SystemAlertDTO {
    /** Unique ID for the alert */
    private int id;

    /** Category/type of the alert (e.g., Energy, System, Maintenance) */
    private String alertCategory;

    /** Vehicle this alert is related to */
    private String vehicleRef;

    /** Descriptive message about the alert */
    private String alertMessage;

    /** Date and time when the alert was triggered */
    private LocalDateTime alertTime;

    /** State of the alert: Pending or Resolved */
    private String resolutionState;

    /**
     * Default constructor
     */
    public SystemAlertDTO() {}

    /**
     * Full constructor for system alerts.
     * 
     * @param id alert ID
     * @param alertCategory type of alert
     * @param vehicleRef vehicle associated
     * @param alertMessage message to display
     * @param alertTime when the alert was created
     * @param resolutionState current resolution status
     */
    public SystemAlertDTO(int id, String alertCategory, String vehicleRef, String alertMessage, LocalDateTime alertTime, String resolutionState) {
        this.id = id;
        this.alertCategory = alertCategory;
        this.vehicleRef = vehicleRef;
        this.alertMessage = alertMessage;
        this.alertTime = alertTime;
        this.resolutionState = resolutionState;
    }

    /** @return alert ID */
    public int getId() { return id; }

    /** @param id alert ID */
    public void setId(int id) { this.id = id; }

    /** @return category of the alert */
    public String getAlertCategory() { return alertCategory; }

    /** @param alertCategory e.g. System, Energy */
    public void setAlertCategory(String alertCategory) { this.alertCategory = alertCategory; }

    /** @return vehicle reference ID */
    public String getVehicleRef() { return vehicleRef; }

    /** @param vehicleRef vehicle related to the alert */
    public void setVehicleRef(String vehicleRef) { this.vehicleRef = vehicleRef; }

    /** @return detailed message of the alert */
    public String getAlertMessage() { return alertMessage; }

    /** @param alertMessage system's alert message */
    public void setAlertMessage(String alertMessage) { this.alertMessage = alertMessage; }

    /** @return timestamp when alert was triggered */
    public LocalDateTime getAlertTime() { return alertTime; }

    /** @param alertTime date and time of alert */
    public void setAlertTime(LocalDateTime alertTime) { this.alertTime = alertTime; }

    /** @return current resolution status */
    public String getResolutionState() { return resolutionState; }

    /** @param resolutionState e.g. Pending or Resolved */
    public void setResolutionState(String resolutionState) { this.resolutionState = resolutionState; }
}
