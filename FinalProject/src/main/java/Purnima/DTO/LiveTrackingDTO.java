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
 * Data Transfer Object for live tracking information.
 * This class is used to transfer live GPS data, including display-related
 * information such as vehicle registration and category, between layers.
 * @author Purnima
 */
public class LiveTrackingDTO {
    private int id;
    private String vehicleRef;
    private double gpsLat;
    private double gpsLng;
    private Timestamp recordedAt;
    private String linkedStation;
    private String vehicleRegistration; // For display purposes
    private String vehicleCategory; // For display purposes
    
    /**
     * Default constructor.
     */
    public LiveTrackingDTO() {}
    
    /**
     * Constructor with core tracking fields for creating a new record.
     * * @param id The unique identifier of the tracking log.
     * @param vehicleRef The unique identifier of the vehicle.
     * @param gpsLat The latitude coordinate of the vehicle.
     * @param gpsLng The longitude coordinate of the vehicle.
     * @param recordedAt The timestamp when the location was recorded.
     * @param linkedStation The ID of the nearest station.
     */
    public LiveTrackingDTO(int id, String vehicleRef, double gpsLat, double gpsLng, 
                            Timestamp recordedAt, String linkedStation) {
        this.id = id;
        this.vehicleRef = vehicleRef;
        this.gpsLat = gpsLat;
        this.gpsLng = gpsLng;
        this.recordedAt = recordedAt;
        this.linkedStation = linkedStation;
    }
    
    // Getters and Setters with Javadoc
    
    /**
     * Gets the unique identifier of the tracking log.
     * @return The tracking log ID.
     */
    public int getId() { return id; }
    
    /**
     * Sets the unique identifier of the tracking log.
     * @param id The new tracking log ID.
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
     * Gets the latitude coordinate.
     * @return The latitude.
     */
    public double getGpsLat() { return gpsLat; }
    
    /**
     * Sets the latitude coordinate.
     * @param gpsLat The new latitude.
     */
    public void setGpsLat(double gpsLat) { this.gpsLat = gpsLat; }
    
    /**
     * Gets the longitude coordinate.
     * @return The longitude.
     */
    public double getGpsLng() { return gpsLng; }
    
    /**
     * Sets the longitude coordinate.
     * @param gpsLng The new longitude.
     */
    public void setGpsLng(double gpsLng) { this.gpsLng = gpsLng; }
    
    /**
     * Gets the timestamp when the location was recorded.
     * @return The recorded timestamp.
     */
    public Timestamp getRecordedAt() { return recordedAt; }
    
    /**
     * Sets the timestamp when the location was recorded.
     * @param recordedAt The new recorded timestamp.
     */
    public void setRecordedAt(Timestamp recordedAt) { this.recordedAt = recordedAt; }
    
    /**
     * Gets the unique identifier of the nearest station.
     * @return The linked station ID.
     */
    public String getLinkedStation() { return linkedStation; }
    
    /**
     * Sets the unique identifier of the nearest station.
     * @param linkedStation The new linked station ID.
     */
    public void setLinkedStation(String linkedStation) { this.linkedStation = linkedStation; }
    
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
    
    /**
     * Gets the vehicle's category for display purposes.
     * @return The vehicle category.
     */
    public String getVehicleCategory() { return vehicleCategory; }
    
    /**
     * Sets the vehicle's category for display purposes.
     * @param vehicleCategory The new vehicle category.
     */
    public void setVehicleCategory(String vehicleCategory) { this.vehicleCategory = vehicleCategory; }
}
