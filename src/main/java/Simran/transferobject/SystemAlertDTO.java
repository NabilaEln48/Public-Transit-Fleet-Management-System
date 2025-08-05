/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Simran.transferobject;

/**
 *
 * @author simra
 */
import java.time.LocalDateTime;

public class SystemAlertDTO {
    private int id;
    private String alertCategory;
    private String vehicleRef;
    private String alertMessage;
    private LocalDateTime alertTime;
    private String resolutionState;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAlertCategory() { return alertCategory; }
    public void setAlertCategory(String alertCategory) { this.alertCategory = alertCategory; }

    public String getVehicleRef() { return vehicleRef; }
    public void setVehicleRef(String vehicleRef) { this.vehicleRef = vehicleRef; }

    public String getAlertMessage() { return alertMessage; }
    public void setAlertMessage(String alertMessage) { this.alertMessage = alertMessage; }

    public LocalDateTime getAlertTime() { return alertTime; }
    public void setAlertTime(LocalDateTime alertTime) { this.alertTime = alertTime; }

    public String getResolutionState() { return resolutionState; }
    public void setResolutionState(String resolutionState) { this.resolutionState = resolutionState; }
}