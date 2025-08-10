/*
 * Assessment: Group Assignment
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Purnima Purnima
 * Student Id: 041161250
 * Professor Name: Teddy Yap
 */
package Purnima.BusinessLayer;

import java.sql.Timestamp;


/**
 * GPS Location DTO for tracking vehicle positions.
 * This class serves as a Data Transfer Object (DTO) to encapsulate GPS location
 * data for vehicles, including their ID, coordinates, and timestamp.
 * * @author Purnima
 */
public class GPSLocationDTO {
	
	/**
	 * The unique identifier for the GPS location record.
	 */
	private int id;
	
	/**
	 * The reference or identifier of the vehicle associated with this location.
	 */
	private String vehicleRef;
	
	/**
	 * The latitude coordinate of the vehicle's GPS position.
	 */
	private double gpsLat;
	
	/**
	 * The longitude coordinate of the vehicle's GPS position.
	 */
	private double gpsLng;
	
	/**
	 * The timestamp indicating when the location was recorded.
	 */
	private Timestamp recordedAt;
	
	/**
	 * The name or identifier of the station linked to this GPS location.
	 */
	private String linkedStation;
	
	/**
	 * Default constructor for GPSLocationDTO.
	 */
	public GPSLocationDTO() {}
	
	/**
	 * Gets the unique ID of the GPS location record.
	 *
	 * @return The ID of the record.
	 */
	public int getId() { return id; }
	
	/**
	 * Sets the unique ID of the GPS location record.
	 *
	 * @param id The ID to set for the record.
	 */
	public void setId(int id) { this.id = id; }
	
	/**
	 * Gets the reference of the vehicle.
	 *
	 * @return The vehicle reference string.
	 */
	public String getVehicleRef() { return vehicleRef; }
	
	/**
	 * Sets the reference of the vehicle.
	 *
	 * @param vehicleRef The vehicle reference string to set.
	 */
	public void setVehicleRef(String vehicleRef) { this.vehicleRef = vehicleRef; }
	
	/**
	 * Gets the GPS latitude coordinate.
	 *
	 * @return The latitude coordinate as a double.
	 */
	public double getGpsLat() { return gpsLat; }
	
	/**
	 * Sets the GPS latitude coordinate.
	 *
	 * @param gpsLat The latitude coordinate to set.
	 */
	public void setGpsLat(double gpsLat) { this.gpsLat = gpsLat; }
	
	/**
	 * Gets the GPS longitude coordinate.
	 *
	 * @return The longitude coordinate as a double.
	 */
	public double getGpsLng() { return gpsLng; }
	
	/**
	 * Sets the GPS longitude coordinate.
	 *
	 * @param gpsLng The longitude coordinate to set.
	 */
	public void setGpsLng(double gpsLng) { this.gpsLng = gpsLng; }
	
	/**
	 * Gets the timestamp of when the location was recorded.
	 *
	 * @return The recorded timestamp.
	 */
	public Timestamp getRecordedAt() { return recordedAt; }
	
	/**
	 * Sets the timestamp of when the location was recorded.
	 *
	 * @param recordedAt The timestamp to set.
	 */
	public void setRecordedAt(Timestamp recordedAt) { this.recordedAt = recordedAt; }
	
	/**
	 * Gets the identifier of the linked station.
	 *
	 * @return The linked station identifier string.
	 */
	public String getLinkedStation() { return linkedStation; }
	
	/**
	 * Sets the identifier of the linked station.
	 *
	 * @param linkedStation The linked station identifier to set.
	 */
	public void setLinkedStation(String linkedStation) { this.linkedStation = linkedStation; }
	
	/**
	 * Provides a string representation of the GPSLocationDTO object.
	 *
	 * @return A string containing all the object's field values.
	 */
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