/*
 * Assessment: Group Assignment
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Purnima Purnima
 * Student Id: 041161250
 * Professor Name: Teddy Yap
 */
package Purnima.BusinessLayer;

/**
 * Data Transfer Object (DTO) for aggregated vehicle statistics.
 * <p>
 * This class is designed to hold aggregated data about the fleet, such as
 * vehicle counts by operational status and category. It is a simple data
 * structure with a constructor, getters, and setters, intended for use
 * in reporting or as a return type for service methods that calculate
 * fleet-wide metrics.
 * </p>
 *
 * @author Purnima
 */
public class VehicleStatistics {
	/** The total number of vehicles in the fleet. */
	private int totalVehicles;
	
	/** The number of vehicles currently in active service. */
	private int activeVehicles;
	
	/** The number of vehicles currently undergoing maintenance. */
	private int maintenanceVehicles;
	
	/** The number of vehicles that are currently inactive or offline. */
	private int inactiveVehicles;
	
	/** The number of diesel-powered buses. */
	private int dieselBuses;
	
	/** The number of electric rail vehicles. */
	private int electricRail;
	
	/** The number of diesel-powered trains. */
	private int dieselTrains;
	
	/**
	 * Parameterized constructor to initialize all vehicle statistics.
	 *
	 * @param totalVehicles The total number of vehicles.
	 * @param activeVehicles The count of active vehicles.
	 * @param maintenanceVehicles The count of vehicles in maintenance.
	 * @param inactiveVehicles The count of inactive vehicles.
	 * @param dieselBuses The count of diesel buses.
	 * @param electricRail The count of electric rail vehicles.
	 * @param dieselTrains The count of diesel trains.
	 */
	public VehicleStatistics(int totalVehicles, int activeVehicles, int maintenanceVehicles,
							 int inactiveVehicles, int dieselBuses, int electricRail, int dieselTrains) {
		this.totalVehicles = totalVehicles;
		this.activeVehicles = activeVehicles;
		this.maintenanceVehicles = maintenanceVehicles;
		this.inactiveVehicles = inactiveVehicles;
		this.dieselBuses = dieselBuses;
		this.electricRail = electricRail;
		this.dieselTrains = dieselTrains;
	}
	
	/**
	 * Gets the total number of vehicles in the fleet.
	 * @return The total vehicle count.
	 */
	public int getTotalVehicles() {
		return totalVehicles;
	}
	
	/**
	 * Sets the total number of vehicles in the fleet.
	 * @param totalVehicles The new total vehicle count.
	 */
	public void setTotalVehicles(int totalVehicles) {
		this.totalVehicles = totalVehicles;
	}
	
	/**
	 * Gets the number of vehicles currently in active service.
	 * @return The count of active vehicles.
	 */
	public int getActiveVehicles() {
		return activeVehicles;
	}
	
	/**
	 * Sets the number of vehicles currently in active service.
	 * @param activeVehicles The new active vehicle count.
	 */
	public void setActiveVehicles(int activeVehicles) {
		this.activeVehicles = activeVehicles;
	}
	
	/**
	 * Gets the number of vehicles currently undergoing maintenance.
	 * @return The count of vehicles in maintenance.
	 */
	public int getMaintenanceVehicles() {
		return maintenanceVehicles;
	}
	
	/**
	 * Sets the number of vehicles currently undergoing maintenance.
	 * @param maintenanceVehicles The new count of vehicles in maintenance.
	 */
	public void setMaintenanceVehicles(int maintenanceVehicles) {
		this.maintenanceVehicles = maintenanceVehicles;
	}
	
	/**
	 * Gets the number of vehicles that are currently inactive.
	 * @return The count of inactive vehicles.
	 */
	public int getInactiveVehicles() {
		return inactiveVehicles;
	}
	
	/**
	 * Sets the number of vehicles that are currently inactive.
	 * @param inactiveVehicles The new count of inactive vehicles.
	 */
	public void setInactiveVehicles(int inactiveVehicles) {
		this.inactiveVehicles = inactiveVehicles;
	}
	
	/**
	 * Gets the number of diesel-powered buses.
	 * @return The count of diesel buses.
	 */
	public int getDieselBuses() {
		return dieselBuses;
	}
	
	/**
	 * Sets the number of diesel-powered buses.
	 * @param dieselBuses The new count of diesel buses.
	 */
	public void setDieselBuses(int dieselBuses) {
		this.dieselBuses = dieselBuses;
	}
	
	/**
	 * Gets the number of electric rail vehicles.
	 * @return The count of electric rail vehicles.
	 */
	public int getElectricRail() {
		return electricRail;
	}
	
	/**
	 * Sets the number of electric rail vehicles.
	 * @param electricRail The new count of electric rail vehicles.
	 */
	public void setElectricRail(int electricRail) {
		this.electricRail = electricRail;
	}
	
	/**
	 * Gets the number of diesel-powered trains.
	 * @return The count of diesel trains.
	 */
	public int getDieselTrains() {
		return dieselTrains;
	}
	
	/**
	 * Sets the number of diesel-powered trains.
	 * @param dieselTrains The new count of diesel trains.
	 */
	public void setDieselTrains(int dieselTrains) {
		this.dieselTrains = dieselTrains;
	}
	
	/**
	 * Generates a string representation of the object.
	 * @return A string containing all the field values.
	 */
	@Override
	public String toString() {
		return "VehicleStatistics{" +
			"totalVehicles=" + totalVehicles +
			", activeVehicles=" + activeVehicles +
			", maintenanceVehicles=" + maintenanceVehicles +
			", inactiveVehicles=" + inactiveVehicles +
			", dieselBuses=" + dieselBuses +
			", electricRail=" + electricRail +
			", dieselTrains=" + dieselTrains +
			'}';
	}
}
