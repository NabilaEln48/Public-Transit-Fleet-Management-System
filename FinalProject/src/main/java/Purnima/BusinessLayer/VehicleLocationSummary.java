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
 * Data Transfer Object (DTO) for summarizing vehicle location information.
 * <p>
 * This class encapsulates key details about a vehicle's current location,
 * operational status, and route assignment. It is designed to be used
 * by the business layer to provide a clean and concise summary of a vehicle
 * for display or reporting purposes.
 * </p>
 *
 * @author Purnima
 */
public class VehicleLocationSummary {
	/** The unique identifier for the vehicle. */
	private String vehicleId;
	
	/** The category or type of the vehicle (e.g., "Bus", "Tram"). */
	private String vehicleCategory;
	
	/** The route the vehicle is currently assigned to. */
	private String assignedRoute;
	
	/** The current GPS latitude of the vehicle. */
	private double currentLat;
	
	/** The current GPS longitude of the vehicle. */
	private double currentLng;
	
	/** The timestamp of the last recorded location update. */
	private Timestamp lastUpdate;
	
	/** The identifier of the station the vehicle is currently at, if any. */
	private String currentStation;
	
	/** The current operational state of the vehicle (e.g., "Active", "In Maintenance"). */
	private String operationalState;
	
	/**
	 * Default constructor.
	 * Initializes a new instance with default values.
	 */
	public VehicleLocationSummary() {}
	
	/**
	 * Parameterized constructor to create a full summary object.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @param vehicleCategory The category of the vehicle.
	 * @param assignedRoute The route assigned to the vehicle.
	 * @param currentLat The current latitude.
	 * @param currentLng The current longitude.
	 * @param lastUpdate The timestamp of the last update.
	 * @param currentStation The current station ID.
	 * @param operationalState The operational state.
	 */
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
	
	/**
	 * Gets the unique identifier of the vehicle.
	 * @return The vehicle ID.
	 */
	public String getVehicleId() { return vehicleId; }
	
	/**
	 * Sets the unique identifier of the vehicle.
	 * @param vehicleId The new vehicle ID.
	 */
	public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }
	
	/**
	 * Gets the vehicle category.
	 * @return The vehicle category.
	 */
	public String getVehicleCategory() { return vehicleCategory; }
	
	/**
	 * Sets the vehicle category.
	 * @param vehicleCategory The new vehicle category.
	 */
	public void setVehicleCategory(String vehicleCategory) { this.vehicleCategory = vehicleCategory; }
	
	/**
	 * Gets the assigned route of the vehicle.
	 * @return The assigned route.
	 */
	public String getAssignedRoute() { return assignedRoute; }
	
	/**
	 * Sets the assigned route of the vehicle.
	 * @param assignedRoute The new assigned route.
	 */
	public void setAssignedRoute(String assignedRoute) { this.assignedRoute = assignedRoute; }
	
	/**
	 * Gets the current latitude.
	 * @return The current latitude.
	 */
	public double getCurrentLat() { return currentLat; }
	
	/**
	 * Sets the current latitude.
	 * @param currentLat The new latitude.
	 */
	public void setCurrentLat(double currentLat) { this.currentLat = currentLat; }
	
	/**
	 * Gets the current longitude.
	 * @return The current longitude.
	 */
	public double getCurrentLng() { return currentLng; }
	
	/**
	 * Sets the current longitude.
	 * @param currentLng The new longitude.
	 */
	public void setCurrentLng(double currentLng) { this.currentLng = currentLng; }
	
	/**
	 * Gets the timestamp of the last update.
	 * @return The last update timestamp.
	 */
	public Timestamp getLastUpdate() { return lastUpdate; }
	
	/**
	 * Sets the timestamp of the last update.
	 * @param lastUpdate The new last update timestamp.
	 */
	public void setLastUpdate(Timestamp lastUpdate) { this.lastUpdate = lastUpdate; }
	
	/**
	 * Gets the current station ID.
	 * @return The current station ID.
	 */
	public String getCurrentStation() { return currentStation; }
	
	/**
	 * Sets the current station ID.
	 * @param currentStation The new current station ID.
	 */
	public void setCurrentStation(String currentStation) { this.currentStation = currentStation; }
	
	/**
	 * Gets the operational state.
	 * @return The operational state.
	 */
	public String getOperationalState() { return operationalState; }
	
	/**
	 * Sets the operational state.
	 * @param operationalState The new operational state.
	 */
	public void setOperationalState(String operationalState) { this.operationalState = operationalState; }
	
	/**
	 * Generates a string representation of the object.
	 * @return A string containing all the field values.
	 */
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
