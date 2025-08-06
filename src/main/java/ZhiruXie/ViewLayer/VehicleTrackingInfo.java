// File: VehicleTrackingInfo.java
package ZhiruXie.ViewLayer;

import java.sql.Timestamp;

/**
 * Helper class to hold vehicle tracking information for dashboard display
 */
public class VehicleTrackingInfo {
    private String vehicleId;
    private String vehicleNumber;
    private String routeName;
    private String stationName;
    private String eventType;
    private Timestamp timestamp;
    private Double gpsLat;
    private Double gpsLng;
    
    // Default constructor
    public VehicleTrackingInfo() {}
    
    // Getters and Setters
    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }
    
    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    
    public String getRouteName() { return routeName; }
    public void setRouteName(String routeName) { this.routeName = routeName; }
    
    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }
    
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    
    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
    
    public Double getGpsLat() { return gpsLat; }
    public void setGpsLat(Double gpsLat) { this.gpsLat = gpsLat; }
    
    public Double getGpsLng() { return gpsLng; }
    public void setGpsLng(Double gpsLng) { this.gpsLng = gpsLng; }
}