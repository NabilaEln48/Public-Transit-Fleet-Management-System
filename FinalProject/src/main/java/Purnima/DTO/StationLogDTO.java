/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Purnima Purnima
 * Student Id: 041161250
 * Professor Name: Teddy Yap
 */
package Purnima.DTO;

import java.sql.Timestamp;

/**
 * Data Transfer Object for station log information.
 * This class is used to transfer data related to a vehicle's
 * arrival at or departure from a station.
 * @author Purnima
 */
public class StationLogDTO {
    private int id;
    private String vehicleRef;
    private String stationRef;
    private String actionType; // ARRIVAL or DEPARTURE
    private Timestamp loggedTime;
    private String stationName; // For display
    private String vehicleRegistration; // For display
    
    /**
     * Default constructor.
     */
    public StationLogDTO() {}
    
    /**
     * Constructor for creating a new station log record.
     * @param id The unique identifier of the station log.
     * @param vehicleRef The unique identifier of the vehicle.
     * @param stationRef The unique identifier of the station.
     * @param actionType The type of action (e.g., "ARRIVAL" or "DEPARTURE").
     * @param loggedTime The timestamp of the event.
     */
    public StationLogDTO(int id, String vehicleRef, String stationRef, 
                         String actionType, Timestamp loggedTime) {
        this.id = id;
        this.vehicleRef = vehicleRef;
        this.stationRef = stationRef;
        this.actionType = actionType;
        this.loggedTime = loggedTime;
    }
    
    // Getters and Setters with Javadoc
    
    /**
     * Gets the unique identifier of the station log.
     * @return The log ID.
     */
    public int getId() { return id; }
    /**
     * Sets the unique identifier of the station log.
     * @param id The new log ID.
     */
    public void setId(int id) { this.id = id; }
    
    /**
     * Gets the unique identifier of the vehicle.
     * @return The vehicle reference.
     */
    public String getVehicleRef() { return vehicleRef; }
    /**
     * Sets the unique identifier of the vehicle.
     * @param vehicleRef The new vehicle reference.
     */
    public void setVehicleRef(String vehicleRef) { this.vehicleRef = vehicleRef; }
    
    /**
     * Gets the unique identifier of the station.
     * @return The station reference.
     */
    public String getStationRef() { return stationRef; }
    /**
     * Sets the unique identifier of the station.
     * @param stationRef The new station reference.
     */
    public void setStationRef(String stationRef) { this.stationRef = stationRef; }
    
    /**
     * Gets the type of action.
     * @return The action type.
     */
    public String getActionType() { return actionType; }
    /**
     * Sets the type of action.
     * @param actionType The new action type.
     */
    public void setActionType(String actionType) { this.actionType = actionType; }
    
    /**
     * Gets the timestamp of the event.
     * @return The logged timestamp.
     */
    public Timestamp getLoggedTime() { return loggedTime; }
    /**
     * Sets the timestamp of the event.
     * @param loggedTime The new logged timestamp.
     */
    public void setLoggedTime(Timestamp loggedTime) { this.loggedTime = loggedTime; }
    
    /**
     * Gets the station's name for display purposes.
     * @return The station name.
     */
    public String getStationName() { return stationName; }
    /**
     * Sets the station's name for display purposes.
     * @param stationName The new station name.
     */
    public void setStationName(String stationName) { this.stationName = stationName; }
    
    /**
     * Gets the vehicle's registration number for display purposes.
     * @return The vehicle registration number.
     */
    public String getVehicleRegistration() { return vehicleRegistration; }
    /**
     * Sets the vehicle's registration number for display purposes.
     * @param vehicleRegistration The new vehicle registration number.
     */
    public void setVehicleRegistration(String vehicleRegistration) { this.vehicleRegistration = vehicleRegistration; }
}
