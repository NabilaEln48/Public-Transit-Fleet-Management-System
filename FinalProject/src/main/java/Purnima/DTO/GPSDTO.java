package Purnima.DTO;


import java.sql.Timestamp;

/**
 * Data Transfer Object for GPS Log information
 * Handles transfer of GPS tracking data between layers
 * * @author Purnima
 */
public class GPSDTO {
    private int id;
    private String vehicleRef;
    private double gpsLat;
    private double gpsLng;
    private Timestamp recordedAt;
    private String linkedStation;

    // Default constructor
    public GPSDTO() {}

    // Constructor with all fields
    public GPSDTO(int id, String vehicleRef, double gpsLat, double gpsLng, 
                    Timestamp recordedAt, String linkedStation) {
        this.id = id;
        this.vehicleRef = vehicleRef;
        this.gpsLat = gpsLat;
        this.gpsLng = gpsLng;
        this.recordedAt = recordedAt;
        this.linkedStation = linkedStation;
    }

    // Constructor without ID (for new records)
    public GPSDTO(String vehicleRef, double gpsLat, double gpsLng, 
                    Timestamp recordedAt, String linkedStation) {
        this.vehicleRef = vehicleRef;
        this.gpsLat = gpsLat;
        this.gpsLng = gpsLng;
        this.recordedAt = recordedAt;
        this.linkedStation = linkedStation;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVehicleRef() {
        return vehicleRef;
    }

    public void setVehicleRef(String vehicleRef) {
        this.vehicleRef = vehicleRef;
    }

    public double getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(double gpsLat) {
        this.gpsLat = gpsLat;
    }

    public double getGpsLng() {
        return gpsLng;
    }

    public void setGpsLng(double gpsLng) {
        this.gpsLng = gpsLng;
    }

    public Timestamp getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(Timestamp recordedAt) {
        this.recordedAt = recordedAt;
    }

    public String getLinkedStation() {
        return linkedStation;
    }

    public void setLinkedStation(String linkedStation) {
        this.linkedStation = linkedStation;
    }

    @Override
    public String toString() {
        return "GPSLogDTO{" +
                "id=" + id +
                ", vehicleRef='" + vehicleRef + '\'' +
                ", gpsLat=" + gpsLat +
                ", gpsLng=" + gpsLng +
                ", recordedAt=" + recordedAt +
                ", linkedStation='" + linkedStation + '\'' +
                '}';
    }
}
