/*
 * Assessment: Group Assignment
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Purnima Purnima
 * Student Id: 041161250
 * Professor Name: Teddy Yap
 */
package Purnima.BusinessLayer;

import Purnima.DataAccessLayer.GPSTrackingDAOImpl;
import ZhiruXie.DataAccessLayer.VehicleDAOImp;
import Purnima.DTO.GPSTrackingDTO;
import Purnima.DTO.ServiceLogDTO;
import Purnima.DTO.StationLogDTO;
import ZhiruXie.DTO.VehicleDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Business Logic Service for GPS Tracking (FR-03).
 * This class handles real-time location tracking and station arrival/departure logging.
 * It encapsulates the business rules and logic, interacting with the Data Access Layer (DAO)
 * and Data Transfer Objects (DTOs) to perform operations and validations.
 *
 * @author Purnima
 */
public class GPSTrackingService {

	/** The logger for this class. */
	private static final Logger LOGGER = Logger.getLogger(GPSTrackingService.class.getName());
	
	/** The Data Access Object for GPS tracking operations. */
	private final GPSTrackingDAOImpl gpsDAO;
	
	/** The Data Access Object for vehicle-related operations. */
	private final VehicleDAOImp vehicleDAO;
	
	// GPS coordinate validation constants
	/** The minimum valid latitude value. */
	private static final double MIN_LATITUDE = -90.0;
	
	/** The maximum valid latitude value. */
	private static final double MAX_LATITUDE = 90.0;
	
	/** The minimum valid longitude value. */
	private static final double MIN_LONGITUDE = -180.0;
	
	/** The maximum valid longitude value. */
	private static final double MAX_LONGITUDE = 180.0;
	
	// Formatter for converting Timestamp to String
	/** A date-time formatter for converting timestamps to a specific string format. */
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * Constructs a new GPSTrackingService instance.
	 *
	 * @param connection The database connection to be used by the DAOs.
	 */
	public GPSTrackingService(Connection connection) {
		this.gpsDAO = new GPSTrackingDAOImpl(connection);
		this.vehicleDAO = new VehicleDAOImp();
	}
	
	/**
	 * A helper method to validate GPS coordinate data.
	 *
	 * @param vehicleId The ID of the vehicle.
	 * @param latitude The GPS latitude.
	 * @param longitude The GPS longitude.
	 * @return A {@link ServiceResult} indicating if the data is valid or not.
	 */
	private ServiceResult validateGPSData(String vehicleId, double latitude, double longitude) {
		if (vehicleId == null || vehicleId.trim().isEmpty()) {
			return new ServiceResult(false, "Vehicle ID cannot be empty.");
		}
		if (latitude < MIN_LATITUDE || latitude > MAX_LATITUDE) {
			return new ServiceResult(false, "Invalid latitude.");
		}
		if (longitude < MIN_LONGITUDE || longitude > MAX_LONGITUDE) {
			return new ServiceResult(false, "Invalid longitude.");
		}
		return new ServiceResult(true, "Validation successful.");
	}
	
	/**
	 * A helper method to validate station log data.
	 *
	 * @param vehicleId The ID of the vehicle.
	 * @param stationId The ID of the station.
	 * @return A {@link ServiceResult} indicating if the data is valid or not.
	 */
	private ServiceResult validateStationLogData(String vehicleId, String stationId) {
		if (vehicleId == null || vehicleId.trim().isEmpty()) {
			return new ServiceResult(false, "Vehicle ID cannot be empty.");
		}
		if (stationId == null || stationId.trim().isEmpty()) {
			return new ServiceResult(false, "Station ID cannot be empty.");
		}
		// Additional checks can be added here (e.g., if vehicle/station exists in DB)
		return new ServiceResult(true, "Validation successful.");
	}
	
	/**
	 * Retrieves the latest GPS data for a specific vehicle.
	 *
	 * @param vehicleId The ID of the vehicle.
	 * @return A {@link GPSLocationDTO} with the latest data, or {@code null} if not found or the ID is invalid.
	 */
	public GPSLocationDTO getLatestVehicleData(String vehicleId) {
		if (vehicleId == null || vehicleId.trim().isEmpty()) {
			LOGGER.warning("Attempted to get latest vehicle data with null or empty ID.");
			return null;
		}
		return gpsDAO.getCurrentLocation(vehicleId);
	}
	
	/**
	 * Retrieves the current location of a vehicle, formatted as a GPSTrackingDTO.
	 * This method is specifically designed for the {@code GPSTrackingServlet} and handles the DTO mapping.
	 *
	 * @param vehicleId The ID of the vehicle.
	 * @return The current location as a {@link GPSTrackingDTO}, or {@code null} if not found.
	 */
	public GPSTrackingDTO getCurrentVehicleLocation(String vehicleId) {
		GPSLocationDTO latestLocation = getLatestVehicleData(vehicleId);
		
		if (latestLocation != null) {
			GPSTrackingDTO trackingDTO = new GPSTrackingDTO();
			trackingDTO.setVehicleId(latestLocation.getVehicleRef());
			trackingDTO.setLatitude(latestLocation.getGpsLat());
			trackingDTO.setLongitude(latestLocation.getGpsLng());
			trackingDTO.setRecordedAt(latestLocation.getRecordedAt());
			trackingDTO.setStationId(latestLocation.getLinkedStation() != null ? Integer.valueOf(latestLocation.getLinkedStation()) : null);
			return trackingDTO;
		}
		return null;
	}
	
	/**
	 * Records a real-time GPS location for a vehicle.
	 * This method validates the input data, checks the vehicle's state, and then
	 * persists the location data to the database.
	 *
	 * @param vehicleId The ID of the vehicle.
	 * @param latitude The GPS latitude.
	 * @param longitude The GPS longitude.
	 * @param stationId The optional station ID if the vehicle is at a station.
	 * @return A {@link ServiceResult} indicating the success or failure of the operation.
	 */
	public ServiceResult recordGPSLocation(String vehicleId, double latitude, double longitude, String stationId) {
		LOGGER.info("Recording GPS location for vehicle: " + vehicleId);
		
		ServiceResult validationResult = validateGPSData(vehicleId, latitude, longitude);
		if (!validationResult.isSuccess()) {
			return validationResult;
		}
		
		VehicleDTO vehicle = null;
		try {
			vehicle = (VehicleDTO) vehicleDAO.getSingleById(vehicleId);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error checking for vehicle existence", e);
			return new ServiceResult(false, "System error while validating vehicle.");
		}
		
		if (vehicle == null) {
			return new ServiceResult(false, "Vehicle does not exist");
		}
		
		if (!"Active".equals(vehicle.getOperationalState())) {
			return new ServiceResult(false, "GPS tracking only available for active vehicles");
		}
		
		try {
			GPSLocationDTO gpsLocation = new GPSLocationDTO();
			gpsLocation.setVehicleRef(vehicleId);
			gpsLocation.setGpsLat(latitude);
			gpsLocation.setGpsLng(longitude);
			gpsLocation.setRecordedAt(Timestamp.valueOf(LocalDateTime.now()));
			gpsLocation.setLinkedStation(stationId);
			
			boolean success = gpsDAO.recordGPSLocation(gpsLocation);
			if (success) {
				LOGGER.info("GPS location recorded successfully for vehicle: " + vehicleId);
				return new ServiceResult(true, "GPS location recorded successfully");
			} else {
				return new ServiceResult(false, "Failed to record GPS location");
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error recording GPS location", e);
			return new ServiceResult(false, "System error occurred while recording GPS location");
		}
	}
	
	/**
	 * Retrieves the current location of a vehicle using its ID.
	 *
	 * @param vehicleId The ID of the vehicle.
	 * @return A {@link GPSLocationDTO} containing the current location, or {@code null} if not found or the ID is invalid.
	 */
	public GPSLocationDTO getCurrentLocation(String vehicleId) {
		if (vehicleId == null || vehicleId.trim().isEmpty()) {
			LOGGER.warning("Invalid vehicle ID provided for location lookup");
			return null;
		}
		return gpsDAO.getCurrentLocation(vehicleId);
	}
	
	/**
	 * Retrieves the location history for a vehicle within a specified date range.
	 *
	 * @param vehicleId The ID of the vehicle.
	 * @param startDate The start date of the history range.
	 * @param endDate The end date of the history range.
	 * @return A {@link List} of {@link GPSLocationDTO} objects representing the location history.
	 */
	public List<GPSLocationDTO> getLocationHistory(String vehicleId, Timestamp startDate, Timestamp endDate) {
		if (vehicleId == null || vehicleId.trim().isEmpty()) {
			LOGGER.warning("Invalid vehicle ID provided for location history");
			return new ArrayList<>();
		}
		
		if (startDate == null || endDate == null) {
			LOGGER.warning("Invalid date range provided for location history");
			return new ArrayList<>();
		}
		
		if (startDate.after(endDate)) {
			LOGGER.warning("Start date cannot be after end date");
			return new ArrayList<>();
		}
		
		String startDateStr = startDate.toLocalDateTime().format(DATE_TIME_FORMATTER);
		String endDateStr = endDate.toLocalDateTime().format(DATE_TIME_FORMATTER);
		
		return gpsDAO.getLocationHistory(vehicleId, startDateStr, endDateStr);
	}
	
	/**
	 * Retrieves recent GPS data for all vehicles, limited by a specified count.
	 *
	 * @param limit The maximum number of records to return.
	 * @return A {@link List} of recent {@link GPSTrackingDTO} objects.
	 */
	public List<GPSTrackingDTO> getRecentGPSData(int limit) {
		try {
			return gpsDAO.getRecentGPSData(limit);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error getting recent GPS data", e);
			return new ArrayList<>();
		}
	}
	
	/**
	 * Retrieves all vehicles that have recent GPS data, indicating they are active.
	 *
	 * @return A {@link List} of active {@link GPSTrackingDTO} objects.
	 */
	public List<GPSTrackingDTO> getActiveVehicles() {
		try {
			return gpsDAO.getActiveVehicles();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error getting active vehicles", e);
			return new ArrayList<>();
		}
	}
	
	/**
	 * Retrieves the full tracking history for a specific vehicle.
	 *
	 * @param vehicleId The ID of the vehicle.
	 * @return A {@link List} of {@link GPSTrackingDTO} objects representing the vehicle's history.
	 */
	public List<GPSTrackingDTO> getVehicleTrackingHistory(String vehicleId) {
		try {
			return gpsDAO.getVehicleTrackingHistory(vehicleId);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error getting vehicle tracking history", e);
			return new ArrayList<>();
		}
	}
	
	/**
	 * Retrieves the tracking history for a vehicle within a specified date range.
	 *
	 * @param vehicleId The ID of the vehicle.
	 * @param startDate A string representing the start date.
	 * @param endDate A string representing the end date.
	 * @return A {@link List} of {@link GPSTrackingDTO} objects for the specified time frame.
	 */
	public List<GPSTrackingDTO> getVehicleTrackingHistory(String vehicleId, String startDate, String endDate) {
		try {
			return gpsDAO.getVehicleTrackingHistory(vehicleId, startDate, endDate);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error getting vehicle tracking history with date range", e);
			return new ArrayList<>();
		}
	}
	
	/**
	 * Retrieves all GPS history for all vehicles within a specified date range.
	 *
	 * @param startDate A string representing the start date.
	 * @param endDate A string representing the end date.
	 * @return A {@link List} of all {@link GPSTrackingDTO} objects within the time frame.
	 */
	public List<GPSTrackingDTO> getAllGPSHistory(String startDate, String endDate) {
		try {
			return gpsDAO.getAllGPSHistory(startDate, endDate);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error getting all GPS history", e);
			return new ArrayList<>();
		}
	}
	
	/**
	 * Retrieves a location report for a specific vehicle within a date range.
	 * This method is an alias for {@link #getVehicleTrackingHistory(String, String, String)}.
	 *
	 * @param vehicleId The ID of the vehicle.
	 * @param startDate A string representing the start date.
	 * @param endDate A string representing the end date.
	 * @return A {@link List} of {@link GPSTrackingDTO} objects.
	 */
	public List<GPSTrackingDTO> getVehicleLocationReport(String vehicleId, String startDate, String endDate) {
		return getVehicleTrackingHistory(vehicleId, startDate, endDate);
	}
	
	/**
	 * Retrieves a location report for all vehicles within a date range.
	 * This method is an alias for {@link #getAllGPSHistory(String, String)}.
	 *
	 * @param startDate A string representing the start date.
	 * @param endDate A string representing the end date.
	 * @return A {@link List} of {@link GPSTrackingDTO} objects.
	 */
	public List<GPSTrackingDTO> getAllVehiclesLocationReport(String startDate, String endDate) {
		return getAllGPSHistory(startDate, endDate);
	}
	
	/**
	 * Retrieves a report of all service breaks within a specified date range.
	 *
	 * @param startDate A string representing the start date.
	 * @param endDate A string representing the end date.
	 * @return A {@link List} of {@link ServiceLogDTO} objects representing the breaks.
	 */
	public List<ServiceLogDTO> getBreaksReport(String startDate, String endDate) {
		try {
			return gpsDAO.getBreaksReport(startDate, endDate);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error getting breaks report", e);
			return new ArrayList<>();
		}
	}
	
	/**
	 * Logs a new break or out-of-service event.
	 *
	 * @param serviceLog The DTO containing the event details.
	 * @return A {@link ServiceResult} indicating success or failure.
	 */
	public ServiceResult logServiceEvent(ServiceLogDTO serviceLog) {
		try {
			if (gpsDAO.logServiceEvent(serviceLog)) {
				return new ServiceResult(true, "Service event logged successfully.");
			} else {
				return new ServiceResult(false, "Failed to log service event.");
			}
		} catch (Exception e) {
			System.err.println("Error in GPSTrackingService.logServiceEvent: " + e.getMessage());
			e.printStackTrace();
			return new ServiceResult(false, "System error: " + e.getMessage());
		}
	}
	
	/**
	 * Logs a new GPS location event using a DTO.
	 *
	 * @param gpsEvent The DTO containing the GPS details.
	 * @return A {@link ServiceResult} indicating success or failure.
	 */
	public ServiceResult logGPSEvent(GPSTrackingDTO gpsEvent) {
		try {
			if (gpsDAO.logGPSEvent(gpsEvent)) {
				return new ServiceResult(true, "GPS event logged successfully.");
			} else {
				return new ServiceResult(false, "Failed to log GPS event.");
			}
		} catch (Exception e) {
			System.err.println("Error in GPSTrackingService.logGPSEvent: " + e.getMessage());
			e.printStackTrace();
			return new ServiceResult(false, "System error: " + e.getMessage());
		}
	}
	
	/**
	 * Logs a vehicle's arrival at a station.
	 *
	 * @param vehicleId The ID of the vehicle.
	 * @param stationId The ID of the station.
	 * @return A {@link ServiceResult} indicating success or failure.
	 */
	public ServiceResult logArrival(String vehicleId, String stationId) {
		LOGGER.info("Logging arrival for vehicle: " + vehicleId + " at station: " + stationId);
		
		ServiceResult validationResult = validateStationLogData(vehicleId, stationId);
		if (!validationResult.isSuccess()) {
			return validationResult;
		}
		
		try {
			StationLogDTO stationLog = new StationLogDTO();
			stationLog.setVehicleRef(vehicleId);
			stationLog.setStationRef(stationId);
			stationLog.setActionType("ARRIVAL");
			stationLog.setLoggedTime(Timestamp.valueOf(LocalDateTime.now()));
			
			boolean success = gpsDAO.logStationEvent(stationLog);
			if (success) {
				LOGGER.info("Arrival logged successfully for vehicle: " + vehicleId);
				return new ServiceResult(true, "Arrival logged successfully");
			} else {
				return new ServiceResult(false, "Failed to log arrival");
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error logging arrival", e);
			return new ServiceResult(false, "System error occurred while logging arrival");
		}
	}
	
	/**
	 * Logs a vehicle's departure from a station.
	 *
	 * @param vehicleId The ID of the vehicle.
	 * @param stationId The ID of the station.
	 * @return A {@link ServiceResult} indicating success or failure.
	 */
	public ServiceResult logDeparture(String vehicleId, String stationId) {
		LOGGER.info("Logging departure for vehicle: " + vehicleId + " from station: " + stationId);
		
		ServiceResult validationResult = validateStationLogData(vehicleId, stationId);
		if (!validationResult.isSuccess()) {
			return validationResult;
		}
		
		try {
			StationLogDTO stationLog = new StationLogDTO();
			stationLog.setVehicleRef(vehicleId);
			stationLog.setStationRef(stationId);
			stationLog.setActionType("DEPARTURE");
			stationLog.setLoggedTime(Timestamp.valueOf(LocalDateTime.now()));
			
			boolean success = gpsDAO.logStationEvent(stationLog);
			if (success) {
				LOGGER.info("Departure logged successfully for vehicle: " + vehicleId);
				return new ServiceResult(true, "Departure logged successfully");
			} else {
				return new ServiceResult(false, "Failed to log departure");
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error logging departure", e);
			return new ServiceResult(false, "System error occurred while logging departure");
		}
	}
	
	/**
	 * Logs the end of a break or out-of-service event for a vehicle.
	 * This method updates an existing service log record with an end time.
	 *
	 * @param vehicleId The vehicle ID.
	 * @param operatorId The operator ID.
	 * @param logType The type of log, either "BREAK" or "OUT_OF_SERVICE".
	 * @return A {@link ServiceResult} indicating success or failure.
	 */
	public ServiceResult logBreakEnd(String vehicleId, int operatorId, String logType) {
		try {
			boolean success = gpsDAO.updateServiceLogEnd(vehicleId, operatorId, logType);
			if (success) {
				return new ServiceResult(true, "Break end event logged successfully.");
			} else {
				return new ServiceResult(false, "Failed to log break end event. There may be no active break to end.");
			}
		} catch (Exception e) {
			System.err.println("Error in GPSTrackingService.logBreakEnd: " + e.getMessage());
			e.printStackTrace();
			return new ServiceResult(false, "System error: " + e.getMessage());
		}
	}
}