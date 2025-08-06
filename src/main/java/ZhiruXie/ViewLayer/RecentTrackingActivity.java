
package ZhiruXie.ViewLayer;

import java.sql.Timestamp;

import java.sql.Timestamp;

import java.sql.Timestamp;

/**
 * Helper class to hold recent tracking activity information for dashboard display
 */
public class RecentTrackingActivity {
    private String vehicleNumber;
    private String eventType;
    private Timestamp timestamp;
    private String stationName;
    private String notes;
    
    // Default constructor
    public RecentTrackingActivity() {}
    
    // Getters and Setters
    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    
    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
    
    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}