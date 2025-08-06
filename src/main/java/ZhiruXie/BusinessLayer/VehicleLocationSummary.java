
package ZhiruXie.BusinessLayer;

import java.sql.Timestamp;

/**
 * Vehicle Location Summary for route tracking
 * 
 * @author Purnima
 */
public class VehicleLocationSummary {
    private String vehicleId;
    private String vehicleCategory;
    private String assignedRoute;
    private double currentLat;
    private double currentLng;
    private Timestamp lastUpdate;
    private String currentStation;
    private String operationalState;
    
    // Default constructor
    public VehicleLocationSummary() {}
    
    // Parameterized constructor
    public VehicleLocationSummary(String vehicleId, String vehicleCategory, String assignedRoute,
                                double currentLat, double currentLng, Timestamp lastUpdate,
                                String currentStation, String operationalState) {
        this.vehicleId = vehicleId;
        this.vehicleCategory = vehicleCategory;
        this.assignedRoute = assignedRoute;
        this.currentLat = currentLat;
        this.currentLng = currentLng;
        this.lastUpdate = lastUpdate;
        this.currentStation = currentStation;
        this.operationalState = operationalState;
    }
    
    // Getters and setters
    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }
    
    public String getVehicleCategory() { return vehicleCategory; }
    public void setVehicleCategory(String vehicleCategory) { this.vehicleCategory = vehicleCategory; }
    
    public String getAssignedRoute() { return assignedRoute; }
    public void setAssignedRoute(String assignedRoute) { this.assignedRoute = assignedRoute; }
    
    public double getCurrentLat() { return currentLat; }
    public void setCurrentLat(double currentLat) { this.currentLat = currentLat; }
    
    public double getCurrentLng() { return currentLng; }
    public void setCurrentLng(double currentLng) { this.currentLng = currentLng; }
    
    public Timestamp getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(Timestamp lastUpdate) { this.lastUpdate = lastUpdate; }
    
    public String getCurrentStation() { return currentStation; }
    public void setCurrentStation(String currentStation) { this.currentStation = currentStation; }
    
    public String getOperationalState() { return operationalState; }
    public void setOperationalState(String operationalState) { this.operationalState = operationalState; }
    
    @Override
    public String toString() {
        return "VehicleLocationSummary{" +
                "vehicleId='" + vehicleId + '\'' +
                ", vehicleCategory='" + vehicleCategory + '\'' +
                ", assignedRoute='" + assignedRoute + '\'' +
                ", currentLat=" + currentLat +
                ", currentLng=" + currentLng +
                ", lastUpdate=" + lastUpdate +
                ", currentStation='" + currentStation + '\'' +
                ", operationalState='" + operationalState + '\'' +
                '}';
    }
}