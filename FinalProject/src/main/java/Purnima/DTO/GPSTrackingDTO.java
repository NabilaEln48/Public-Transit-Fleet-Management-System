package Purnima.DTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for GPS Tracking information.
 * Updated to match the database schema and DAO requirements.
 *
 * @author Purnima / Vanessa
 */
public class GPSTrackingDTO {

    private int trackingId;
    private String vehicleId;
    private int operatorId;
    private Integer routeId;
    private Integer stationId;
    private double gpsLat;
    private double gpsLng;
    private Timestamp recordedAt;
    private String linkedStation;
    private String assignedRoute;
    private LocalDateTime expectedArrivalTime;
    private String eventType;
    private Timestamp timestamp;
    private String notes;

    /**
     * Full constructor for retrieving existing GPS tracking records.
     */
    public GPSTrackingDTO(int trackingId, String vehicleId, int operatorId, Integer routeId,
                             Integer stationId, double gpsLat, double gpsLng, Timestamp recordedAt,
                             String linkedStation, String assignedRoute, LocalDateTime expectedArrivalTime,
                             String eventType, Timestamp timestamp, String notes) {
        this.trackingId = trackingId;
        this.vehicleId = vehicleId;
        this.operatorId = operatorId;
        this.routeId = routeId;
        this.stationId = stationId;
        this.gpsLat = gpsLat;
        this.gpsLng = gpsLng;
        this.recordedAt = recordedAt;
        this.linkedStation = linkedStation;
        this.assignedRoute = assignedRoute;
        this.expectedArrivalTime = expectedArrivalTime;
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.notes = notes;
    }

    /**
     * Constructor for creating a new GPS tracking record (without trackingId).
     */
    public GPSTrackingDTO(String vehicleId, int operatorId, Integer routeId, Integer stationId,
                             double gpsLat, double gpsLng, Timestamp recordedAt, String linkedStation,
                             String assignedRoute, LocalDateTime expectedArrivalTime, String eventType,
                             Timestamp timestamp, String notes) {
        this(0, vehicleId, operatorId, routeId, stationId, gpsLat, gpsLng, recordedAt,
             linkedStation, assignedRoute, expectedArrivalTime, eventType, timestamp, notes);
    }
    
    // Default constructor for cases where a full object isn't immediately available
    public GPSTrackingDTO() {
    }

    // --- Getters ---

    public int getTrackingId() {
        return trackingId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public Integer getStationId() {
        return stationId;
    }
    
    public double getGpsLat() {
        return gpsLat;
    }

    public double getGpsLng() {
        return gpsLng;
    }
    
    // Added for compatibility with the service layer
    public double getLatitude() {
        return gpsLat;
    }
    
    // Added for compatibility with the service layer
    public double getLongitude() {
        return gpsLng;
    }

    public Timestamp getRecordedAt() {
        return recordedAt;
    }

    public String getLinkedStation() {
        return linkedStation;
    }

    public String getAssignedRoute() {
        return assignedRoute;
    }

    public LocalDateTime getExpectedArrivalTime() {
        return expectedArrivalTime;
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

    // --- Setters ---

    public void setTrackingId(int trackingId) {
        this.trackingId = trackingId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }
    
    public void setGpsLat(double gpsLat) {
        this.gpsLat = gpsLat;
    }

    public void setGpsLng(double gpsLng) {
        this.gpsLng = gpsLng;
    }
    
    // Added for compatibility with the service layer
    public void setLatitude(double latitude) {
        this.gpsLat = latitude;
    }
    
    // Added for compatibility with the service layer
    public void setLongitude(double longitude) {
        this.gpsLng = longitude;
    }

    public void setRecordedAt(Timestamp recordedAt) {
        this.recordedAt = recordedAt;
    }

    public void setLinkedStation(String linkedStation) {
        this.linkedStation = linkedStation;
    }

    public void setAssignedRoute(String assignedRoute) {
        this.assignedRoute = assignedRoute;
    }

    public void setExpectedArrivalTime(LocalDateTime expectedArrivalTime) {
        this.expectedArrivalTime = expectedArrivalTime;
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

    @Override
    public String toString() {
        return "GPSTrackingDTO{" +
                "trackingId=" + trackingId +
                ", vehicleId='" + vehicleId + '\'' +
                ", operatorId=" + operatorId +
                ", routeId=" + routeId +
                ", stationId=" + stationId +
                ", gpsLat=" + gpsLat +
                ", gpsLng=" + gpsLng +
                ", recordedAt=" + recordedAt +
                ", linkedStation='" + linkedStation + '\'' +
                ", assignedRoute='" + assignedRoute + '\'' +
                ", expectedArrivalTime=" + expectedArrivalTime +
                ", eventType='" + eventType + '\'' +
                ", timestamp=" + timestamp +
                ", notes='" + notes + '\'' +
                '}';
    }
}
