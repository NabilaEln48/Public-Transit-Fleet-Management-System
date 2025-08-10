/*
 * Assessment: Group Assignment
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Purnima Purnima
 * Student Id: 041161250
 * Professor Name: Teddy Yap
 */
package Purnima.DataAccessLayer;

import Purnima.BusinessLayer.GPSLocationDTO;
import Purnima.BusinessLayer.VehicleLocationSummary;
import Purnima.DTO.GPSLogDTO;
import Purnima.DTO.GPSTrackingDTO;
import Purnima.DTO.ServiceLogDTO;
import Purnima.DTO.StationLogDTO;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * GPS Tracking DAO (Data Access Object) Interface.
 * <p>
 * Defines a contract for GPS tracking data access operations. Implementations of this
 * interface are responsible for interacting with a data source (e.g., a database)
 * to perform CRUD (Create, Read, Update, Delete) and other data retrieval tasks
 * related to GPS tracking data.
 * </p>
 */
public interface GPSTrackingDAO {
	
	// Core CRUD and utility methods for GPSTrackingDTO
	/**
	 * Retrieves all GPS tracking records from the data source.
	 *
	 * @return A {@code List} of all {@code GPSTrackingDTO} records.
	 * @throws SQLException if a database access error occurs.
	 */
	List<GPSTrackingDTO> getAll() throws SQLException;
	
	/**
	 * Retrieves a single GPS tracking record by its unique ID.
	 *
	 * @param id The unique ID of the GPS tracking record.
	 * @return The {@code GPSTrackingDTO} matching the ID, or {@code null} if not found.
	 * @throws SQLException if a database access error occurs.
	 */
	GPSTrackingDTO getById(int id) throws SQLException;
	
	/**
	 * Adds a new GPS tracking record to the data source.
	 *
	 * @param dto The {@code GPSTrackingDTO} to be added.
	 * @throws SQLException if a database access error occurs.
	 */
	void add(GPSTrackingDTO dto) throws SQLException;
	
	/**
	 * Updates an existing GPS tracking record in the data source.
	 *
	 * @param dto The {@code GPSTrackingDTO} with updated information.
	 * @throws SQLException if a database access error occurs.
	 */
	void update(GPSTrackingDTO dto) throws SQLException;
	
	/**
	 * Deletes a GPS tracking record from the data source.
	 *
	 * @param dto The {@code GPSTrackingDTO} to be deleted.
	 * @throws SQLException if a database access error occurs.
	 */
	void delete(GPSTrackingDTO dto) throws SQLException;
	
	/**
	 * Prints metadata about the data source.
	 *
	 * @throws SQLException if a database access error occurs.
	 */
	void printMetaData() throws SQLException;
	
	// Methods related to retrieving GPSTrackingDTO records
	/**
	 * Retrieves all GPS tracking records for a specific vehicle.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @return A {@code List} of {@code GPSTrackingDTO} records for the specified vehicle.
	 * @throws SQLException if a database access error occurs.
	 */
	List<GPSTrackingDTO> getByVehicleId(String vehicleId) throws SQLException;
	
	/**
	 * Retrieves all arrival and departure events from the data source.
	 *
	 * @return A {@code List} of {@code GPSTrackingDTO} records representing arrival and departure events.
	 * @throws SQLException if a database access error occurs.
	 */
	List<GPSTrackingDTO> getArrivalDepartureEvents() throws SQLException;
	
	/**
	 * Retrieves a limited number of GPS logs for a specific vehicle within a given date range.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @param dateFrom The start date for the log query (inclusive), in a format understandable by the database.
	 * @param dateTo The end date for the log query (inclusive), in a format understandable by the database.
	 * @param limit The maximum number of records to retrieve.
	 * @return A {@code List} of {@code GPSLogDTO} records.
	 * @throws SQLException if a database access error occurs.
	 */
	List<GPSLogDTO> getGPSLogsByVehicle(String vehicleId, String dateFrom, String dateTo, int limit) throws SQLException;
	
	// Original methods from your existing interface snippet
	/**
	 * Records a new GPS location for a vehicle.
	 *
	 * @param gpsLocation The {@code GPSLocationDTO} containing the location data.
	 * @return {@code true} if the recording was successful, {@code false} otherwise.
	 */
	boolean recordGPSLocation(GPSLocationDTO gpsLocation);
	
	/**
	 * Retrieves the current location of a specific vehicle.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @return The {@code GPSLocationDTO} for the vehicle's current location.
	 */
	GPSLocationDTO getCurrentLocation(String vehicleId);
	
	/**
	 * Retrieves the location history for a specific vehicle within a date range.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @param startDate The start date of the history.
	 * @param endDate The end date of the history.
	 * @return A {@code List} of {@code GPSLocationDTO} records.
	 */
	List<GPSLocationDTO> getLocationHistory(String vehicleId, String startDate, String endDate);
	
	/**
	 * Logs a station event for a vehicle.
	 *
	 * @param stationLog The {@code StationLogDTO} containing the station event data.
	 * @return {@code true} if the logging was successful, {@code false} otherwise.
	 */
	boolean logStationEvent(StationLogDTO stationLog);
	
	/**
	 * Retrieves station log events for a vehicle within a date range.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @param startDate The start date for the logs.
	 * @param endDate The end date for the logs.
	 * @return A {@code List} of {@code StationLogDTO} records.
	 */
	List<StationLogDTO> getStationLogs(String vehicleId, String startDate, String endDate);
	
	/**
	 * Logs a service event for a vehicle.
	 *
	 * @param serviceLog The {@code ServiceLogDTO} containing the service event data.
	 * @return {@code true} if the logging was successful, {@code false} otherwise.
	 */
	boolean logServiceEvent(ServiceLogDTO serviceLog);
	
	/**
	 * Updates the end time for an active service log for a vehicle.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @param operatorId The ID of the operator.
	 * @param logType The type of service log to update.
	 * @return {@code true} if the update was successful, {@code false} otherwise.
	 */
	boolean updateServiceLogEnd(String vehicleId, int operatorId, String logType);
	
	/**
	 * Retrieves a summary of all vehicles currently on a specific route.
	 *
	 * @param route The ID or name of the route.
	 * @return A {@code List} of {@code VehicleLocationSummary} objects.
	 */
	List<VehicleLocationSummary> getVehiclesOnRoute(String route);
	
	/**
	 * Retrieves all active service logs for a specific vehicle.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @return A {@code List} of {@code ServiceLogDTO} records.
	 */
	List<ServiceLogDTO> getActiveServiceLogs(String vehicleId);
	
	/**
	 * Deletes GPS records that are older than a specified cutoff date.
	 *
	 * @param cutoffDate The timestamp before which records should be deleted.
	 * @return The number of records deleted.
	 */
	int deleteOldGPSRecords(Timestamp cutoffDate);
	
	// Additional methods that you already have implemented but need to be in the interface
	/**
	 * Retrieves a limited number of the most recent GPS data records.
	 *
	 * @param limit The maximum number of records to retrieve.
	 * @return A {@code List} of recent {@code GPSTrackingDTO} records.
	 * @throws SQLException if a database access error occurs.
	 */
	List<GPSTrackingDTO> getRecentGPSData(int limit) throws SQLException;
	
	/**
	 * Retrieves a list of all vehicles that are currently active.
	 *
	 * @return A {@code List} of {@code GPSTrackingDTO} records for active vehicles.
	 */
	List<GPSTrackingDTO> getActiveVehicles();
	
	/**
	 * Retrieves the full tracking history for a specific vehicle.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @return A {@code List} of all {@code GPSTrackingDTO} records for the vehicle.
	 */
	List<GPSTrackingDTO> getVehicleTrackingHistory(String vehicleId);
	
	/**
	 * Retrieves the tracking history for a specific vehicle within a date range.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @param startDate The start date of the history.
	 * @param endDate The end date of the history.
	 * @return A {@code List} of {@code GPSTrackingDTO} records.
	 */
	List<GPSTrackingDTO> getVehicleTrackingHistory(String vehicleId, String startDate, String endDate);
	
	/**
	 * Retrieves all GPS history records within a specific date range.
	 *
	 * @param startDate The start date of the history.
	 * @param endDate The end date of the history.
	 * @return A {@code List} of all {@code GPSTrackingDTO} records.
	 */
	List<GPSTrackingDTO> getAllGPSHistory(String startDate, String endDate);
	
	/**
	 * Retrieves a report on service breaks for all vehicles within a date range.
	 *
	 * @param startDate The start date of the report period.
	 * @param endDate The end date of the report period.
	 * @return A {@code List} of {@code ServiceLogDTO} records representing breaks.
	 */
	List<ServiceLogDTO> getBreaksReport(String startDate, String endDate);
	
	/**
	 * Logs a new GPS event.
	 *
	 * @param gpsEvent The {@code GPSTrackingDTO} containing the event data.
	 * @return {@code true} if the logging was successful, {@code false} otherwise.
	 */
	boolean logGPSEvent(GPSTrackingDTO gpsEvent);
	
	// New methods needed for your servlet
	/**
	 * Retrieves the most recent location of a specific vehicle.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @return The current {@code GPSTrackingDTO} for the vehicle.
	 */
	GPSTrackingDTO getCurrentVehicleLocation(String vehicleId);
	
	/**
	 * Retrieves a detailed location report for a single vehicle within a date range.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @param startDate The start date for the report.
	 * @param endDate The end date for the report.
	 * @return A {@code List} of {@code GPSTrackingDTO} records for the report.
	 */
	List<GPSTrackingDTO> getVehicleLocationReport(String vehicleId, String startDate, String endDate);
	
	/**
	 * Retrieves a detailed location report for all vehicles within a date range.
	 *
	 * @param startDate The start date for the report.
	 * @param endDate The end date for the report.
	 * @return A {@code List} of all {@code GPSTrackingDTO} records for the report.
	 */
	List<GPSTrackingDTO> getAllVehiclesLocationReport(String startDate, String endDate);
}
