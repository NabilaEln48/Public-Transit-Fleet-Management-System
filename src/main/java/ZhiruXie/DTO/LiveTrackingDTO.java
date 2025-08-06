package ZhiruXie.DTO;

import java.time.LocalDateTime;
import java.sql.Timestamp;

public class LiveTrackingDTO {
    private int id;
    private String vehicleRef;
    private double gpsLat;
    private double gpsLng;
    private Timestamp recordedAt;
    private String linkedStation;
    private String vehicleRegistration; // For display purposes
    private String vehicleCategory; // For display purposes
    
    // Default constructor
    public LiveTrackingDTO() {}
    
    // Constructor with all fields
    public LiveTrackingDTO(int id, String vehicleRef, double gpsLat, double gpsLng, 
                        Timestamp recordedAt, String linkedStation) {
        this.id = id;
        this.vehicleRef = vehicleRef;
        this.gpsLat = gpsLat;
        this.gpsLng = gpsLng;
        this.recordedAt = recordedAt;
        this.linkedStation = linkedStation;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getVehicleRef() { return vehicleRef; }
    public void setVehicleRef(String vehicleRef) { this.vehicleRef = vehicleRef; }
    
    public double getGpsLat() { return gpsLat; }
    public void setGpsLat(double gpsLat) { this.gpsLat = gpsLat; }
    
    public double getGpsLng() { return gpsLng; }
    public void setGpsLng(double gpsLng) { this.gpsLng = gpsLng; }
    
    public Timestamp getRecordedAt() { return recordedAt; }
    public void setRecordedAt(Timestamp recordedAt) { this.recordedAt = recordedAt; }
    
    public String getLinkedStation() { return linkedStation; }
    public void setLinkedStation(String linkedStation) { this.linkedStation = linkedStation; }
    
    public String getVehicleRegistration() { return vehicleRegistration; }
    public void setVehicleRegistration(String vehicleRegistration) { this.vehicleRegistration = vehicleRegistration; }
    
    public String getVehicleCategory() { return vehicleCategory; }
    public void setVehicleCategory(String vehicleCategory) { this.vehicleCategory = vehicleCategory; }
}
