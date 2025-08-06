package ZhiruXie.DTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
public class StationLogDTO {
    private int id;
    private String vehicleRef;
    private String stationRef;
    private String actionType; // ARRIVAL or DEPARTURE
    private Timestamp loggedTime;
    private String stationName; // For display
    private String vehicleRegistration; // For display
    
    // Default constructor
    public StationLogDTO() {}
    
    // Constructor
    public StationLogDTO(int id, String vehicleRef, String stationRef, 
                        String actionType, Timestamp loggedTime) {
        this.id = id;
        this.vehicleRef = vehicleRef;
        this.stationRef = stationRef;
        this.actionType = actionType;
        this.loggedTime = loggedTime;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getVehicleRef() { return vehicleRef; }
    public void setVehicleRef(String vehicleRef) { this.vehicleRef = vehicleRef; }
    
    public String getStationRef() { return stationRef; }
    public void setStationRef(String stationRef) { this.stationRef = stationRef; }
    
    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }
    
    public Timestamp getLoggedTime() { return loggedTime; }
    public void setLoggedTime(Timestamp loggedTime) { this.loggedTime = loggedTime; }
    
    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }
    
    public String getVehicleRegistration() { return vehicleRegistration; }
    public void setVehicleRegistration(String vehicleRegistration) { this.vehicleRegistration = vehicleRegistration; }
}