package ZhiruXie.DTO;

import java.sql.Timestamp;

/**
 * Data Transfer Object for GPS Log information.
 * This class is separate from GPSTrackingDTO and focuses on GPS logging events.
 *
 * @author Your Name
 */
public class GPSLogDTO {

    private int id;
    private String vehicleId;
    private int operatorId;
    private Double latitude;
    private Double longitude;
    private String eventType;
    private Timestamp timestamp;
    private String notes;
    private String assignedRoute;
    private String linkedStation;

    /**
     * Default constructor.
     */
    public GPSLogDTO() {
    }

    /**
     * Constructor for creating a new GPS log record.
     */
    public GPSLogDTO(String vehicleId, int operatorId, Double latitude, Double longitude,
                     String eventType, Timestamp timestamp, String notes) {
        this.vehicleId = vehicleId;
        this.operatorId = operatorId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.notes = notes;
    }

    /**
     * Full constructor for retrieving existing GPS log records.
     */
    public GPSLogDTO(int id, String vehicleId, int operatorId, Double latitude, Double longitude,
                     String eventType, Timestamp timestamp, String notes, String assignedRoute,
                     String linkedStation) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.operatorId = operatorId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.notes = notes;
        this.assignedRoute = assignedRoute;
        this.linkedStation = linkedStation;
    }

    // --- Getters ---

    public int getId() {
        return id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getEventType() {
        return eventType;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getNotes() {
        return notes;
    }

    public String getAssignedRoute() {
        return assignedRoute;
    }

    public String getLinkedStation() {
        return linkedStation;
    }

    // --- Setters ---

    public void setId(int id) {
        this.id = id;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setAssignedRoute(String assignedRoute) {
        this.assignedRoute = assignedRoute;
    }

    public void setLinkedStation(String linkedStation) {
        this.linkedStation = linkedStation;
    }

    @Override
    public String toString() {
        return "GPSLogDTO{" +
                "id=" + id +
                ", vehicleId='" + vehicleId + '\'' +
                ", operatorId=" + operatorId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", eventType='" + eventType + '\'' +
                ", timestamp=" + timestamp +
                ", notes='" + notes + '\'' +
                ", assignedRoute='" + assignedRoute + '\'' +
                ", linkedStation='" + linkedStation + '\'' +
                '}';
    }
}