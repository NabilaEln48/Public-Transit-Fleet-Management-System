
package ZhiruXie.BusinessLayer;

import java.sql.Timestamp;


/**
 * GPS Location DTO for tracking vehicle positions
 * 
 * @author Purnima
 */
public class GPSLocationDTO {
    private int id;
    private String vehicleRef;
    private double gpsLat;
    private double gpsLng;
    private Timestamp recordedAt;
    private String linkedStation;
    
    // Default constructor
    public GPSLocationDTO() {}
    
    // Getters and setters
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
    
    @Override
    public String toString() {
        return "GPSLocationDTO{" +
                "id=" + id +
                ", vehicleRef='" + vehicleRef + '\'' +
                ", gpsLat=" + gpsLat +
                ", gpsLng=" + gpsLng +
                ", recordedAt=" + recordedAt +
                ", linkedStation='" + linkedStation + '\'' +
                '}';
    }
}
  
