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
 * Data Transfer Object (DTO) for the system_alerts table.
 * 
 * Used to pass system alert data between DAO, Business Logic,
 * and Presentation layers.
 * 
 * Contains details such as alert category, vehicle reference,
 * alert message, timestamp, and resolution state.
 * 
 * @author Prabhsimran Kaur
 */
public class SystemAlertDTO {

    /** Unique identifier of the system alert. */
    private int id;

    /** The category/type of the alert (e.g., Fuel, Energy). */
    private String alertCategory;

    /** The reference ID of the vehicle related to the alert. */
    private String vehicleRef;

    /** The alert message, usually including severity. */
    private String alertMessage;

    /** The timestamp when the alert was triggered. */
    private LocalDateTime alertTime;

    /** The current resolution status of the alert (e.g., Pending, Resolved). */
    private String resolutionState;

    /** @return the alert ID */
    public int getId() { return id; }

    /** @param id the alert ID to set */
    public void setId(int id) { this.id = id; }

    /** @return the alert category */
    public String getAlertCategory() { return alertCategory; }

    /** @param alertCategory the alert category to set */
    public void setAlertCategory(String alertCategory) { this.alertCategory = alertCategory; }

    /** @return the vehicle reference */
    public String getVehicleRef() { return vehicleRef; }

    /** @param vehicleRef the vehicle reference to set */
    public void setVehicleRef(String vehicleRef) { this.vehicleRef = vehicleRef; }

    /** @return the alert message */
    public String getAlertMessage() { return alertMessage; }

    /** @param alertMessage the alert message to set */
    public void setAlertMessage(String alertMessage) { this.alertMessage = alertMessage; }

    /** @return the alert timestamp */
    public LocalDateTime getAlertTime() { return alertTime; }

    /** @param alertTime the alert timestamp to set */
    public void setAlertTime(LocalDateTime alertTime) { this.alertTime = alertTime; }

    /** @return the resolution state */
    public String getResolutionState() { return resolutionState; }

    /** @param resolutionState the resolution state to set */
    public void setResolutionState(String resolutionState) { this.resolutionState = resolutionState; }
}