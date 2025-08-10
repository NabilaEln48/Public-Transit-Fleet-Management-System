/*
 * Assessment: Group Assignment
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Purnima Purnima
 * Student Id: 041161250
 * Professor Name: Teddy Yap
 */
package Purnima.BusinessLayer;

import Purnima.DTO.LiveTrackingDTO;
import Purnima.DTO.ServiceLogDTO;
import Purnima.DTO.StationLogDTO;
import ZhiruXie.DataAccessLayer.*;
import ZhiruXie.DTO.*;
import Purnima.DataAccessLayer.LiveTrackingDAO;
import Purnima.DataAccessLayer.LiveTrackingDAOImpl;
import Purnima.DataAccessLayer.ServiceLogDAO;
import Purnima.DataAccessLayer.ServiceLogDAOImpl;
import Purnima.DataAccessLayer.StationLogDAO;
import Purnima.DataAccessLayer.StationLogDAOImpl;
import ZhiruXie.DataAccessLayer.VehicleDAOImp;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;


/**
 * The Business Logic class for GPS Tracking.
 * This class orchestrates the interaction between the data access layer (DAO)
 * and the presentation layer, handling business rules and processes related
 * to vehicle tracking, station logs, and service logs.
 */
public class GPSTrackingBusinessLogic {
	
	/** The logger for the class to record events and errors. */
	private static final Logger LOGGER = Logger.getLogger(GPSTrackingBusinessLogic.class.getName());
	
	/** The database connection instance. */
	private final Connection connection;
	
	/** The DAO for interacting with live tracking data. */
	private final LiveTrackingDAO liveTrackingDAO;
	
	/** The DAO for interacting with station log data. */
	private final StationLogDAO stationLogDAO;
	
	/** The DAO for interacting with service log data. */
	private final ServiceLogDAO serviceLogDAO;
	
	/** The DAO for interacting with vehicle data. */
	private final VehicleDAOImp vehicleDAO;
	
	/**
	 * Constructs a new GPSTrackingBusinessLogic instance.
	 * Initializes the database connection and the various DAO objects used
	 * by the business logic.
	 *
	 * @throws SQLException if a database access error occurs or the connection
	 * cannot be established.
	 */
	public GPSTrackingBusinessLogic() {
		// Attempt to get the database connection
		this.connection = DataSource.getConnection("cst8288", "cst8288");
		
		// IMPORTANT: Check if the connection is null. If DataSource.getConnection()
		// failed to establish a connection but didn't throw an exception,
		// this explicit check will ensure an SQLException is thrown.
		
		// Pass the valid connection to the DAO constructors
		this.liveTrackingDAO = new LiveTrackingDAOImpl(connection);
		this.stationLogDAO = new StationLogDAOImpl(connection);
		this.serviceLogDAO = new ServiceLogDAOImpl(connection);
		this.vehicleDAO = new VehicleDAOImp(); // Initialize VehicleDAOImpl
	}
	
	/**
	 * Retrieves all live tracking data.
	 *
	 * @return A list of {@link LiveTrackingDTO} objects representing the live tracking data.
	 * @throws UnsupportedOperationException if the method is not yet implemented.
	 */
	public List<LiveTrackingDTO> getAllLiveTracking() {
		// Implement logic to call liveTrackingDAO
		// Example: return liveTrackingDAO.getAllLiveTracking();
		throw new UnsupportedOperationException("Not supported yet."); // Placeholder
	}
	
	/**
	 * Retrieves all tracking data for a specific vehicle.
	 *
	 * @param vehicleId The unique identifier of the vehicle.
	 * @return A list of {@link LiveTrackingDTO} objects for the specified vehicle.
	 * @throws UnsupportedOperationException if the method is not yet implemented.
	 */
	public List<LiveTrackingDTO> getVehicleTracking(String vehicleId) {
		// Implement logic to call liveTrackingDAO
		// Example: return liveTrackingDAO.getTrackingByVehicle(vehicleId);
		throw new UnsupportedOperationException("Not supported yet."); // Placeholder
	}
	
	/**
	 * Retrieves all station arrival/departure logs.
	 *
	 * @return A list of all {@link StationLogDTO} objects.
	 * @throws UnsupportedOperationException if the method is not yet implemented.
	 */
	public List<StationLogDTO> getAllStationLogs() {
		// Implement logic to call stationLogDAO
		// Example: return stationLogDAO.getAllStationLogs();
		throw new UnsupportedOperationException("Not supported yet."); // Placeholder
	}
	
	/**
	 * Retrieves all station logs for a specific vehicle.
	 *
	 * @param vehicleId The unique identifier of the vehicle.
	 * @return A list of {@link StationLogDTO} objects for the specified vehicle.
	 * @throws UnsupportedOperationException if the method is not yet implemented.
	 */
	public List<StationLogDTO> getVehicleStationLogs(String vehicleId) {
		// Implement logic to call stationLogDAO
		// Example: return stationLogDAO.getLogsByVehicle(vehicleId);
		throw new UnsupportedOperationException("Not supported yet."); // Placeholder
	}
	
	/**
	 * Retrieves service logs (breaks/out-of-service) for a specific operator.
	 *
	 * @param operatorId The unique identifier of the operator.
	 * @return A list of {@link ServiceLogDTO} objects for the specified operator.
	 * @throws UnsupportedOperationException if the method is not yet implemented.
	 */
	public List<ServiceLogDTO> getOperatorServiceLogs(int operatorId) {
		// Implement logic to call serviceLogDAO
		// Example: return serviceLogDAO.getLogsByOperator(operatorId);
		throw new UnsupportedOperationException("Not supported yet."); // Placeholder
	}
	
	/**
	 * Records a new GPS location for a vehicle.
	 *
	 * @param vehicleId The unique identifier of the vehicle.
	 * @param lat The latitude of the GPS location.
	 * @param lng The longitude of the GPS location.
	 * @param station The linked station name or identifier.
	 * @return {@code true} if the location was recorded successfully, {@code false} otherwise.
	 * @throws UnsupportedOperationException if the method is not yet implemented.
	 */
	public boolean recordGPSLocation(String vehicleId, double lat, double lng, String station) {
		// Implement logic to record GPS location
		// Example:
		// LiveTrackingDTO tracking = new LiveTrackingDTO();
		// tracking.setVehicleRef(vehicleId);
		// tracking.setGpsLat(lat);
		// tracking.setGpsLng(lng);
		// tracking.setRecordedAt(Timestamp.valueOf(LocalDateTime.now()));
		// tracking.setLinkedStation(station);
		// return liveTrackingDAO.insertTrackingData(tracking);
		throw new UnsupportedOperationException("Not supported yet."); // Placeholder
	}
	
	/**
	 * Logs a station event (e.g., arrival or departure) for a vehicle.
	 *
	 * @param vehicleId The unique identifier of the vehicle.
	 * @param stationId The unique identifier of the station.
	 * @param actionType The type of action (e.g., "ARRIVAL", "DEPARTURE").
	 * @return {@code true} if the station event was logged successfully, {@code false} otherwise.
	 * @throws UnsupportedOperationException if the method is not yet implemented.
	 */
	public boolean logStationEvent(String vehicleId, String stationId, String actionType) {
		// Implement logic to log station event
		// Example:
		// StationLogDTO log = new StationLogDTO();
		// log.setVehicleRef(vehicleId);
		// log.setStationRef(stationId);
		// log.setActionType(actionType);
		// log.setLoggedTime(Timestamp.valueOf(LocalDateTime.now()));
		// return stationLogDAO.insertStationLog(log);
		throw new UnsupportedOperationException("Not supported yet."); // Placeholder
	}
	
	/**
	 * Starts a new service log for a vehicle and operator.
	 * This could be for a break or an out-of-service period.
	 *
	 * @param vehicleId The unique identifier of the vehicle.
	 * @param operatorId The unique identifier of the operator.
	 * @param logType The type of service log (e.g., "BREAK", "OUT_OF_SERVICE").
	 * @return {@code true} if the service log was started successfully, {@code false} otherwise.
	 * @throws UnsupportedOperationException if the method is not yet implemented.
	 */
	public boolean startServiceLog(String vehicleId, int operatorId, String logType) {
		// Implement logic to start service log
		// Example:
		// ServiceLogDTO log = new ServiceLogDTO();
		// log.setVehicleRef(vehicleId);
		// log.setOperatorRef(operatorId);
		// log.setLogType(logType);
		// log.setBreakStart(Timestamp.valueOf(LocalDateTime.now()));
		// return serviceLogDAO.insertServiceLog(log);
		throw new UnsupportedOperationException("Not supported yet."); // Placeholder
	}
	
	/**
	 * Ends an existing service log by providing the log ID.
	 *
	 * @param logId The unique identifier of the service log to end.
	 * @return {@code true} if the service log was ended successfully, {@code false} otherwise.
	 * @throws UnsupportedOperationException if the method is not yet implemented.
	 */
	public boolean endServiceLog(int logId) {
		// Implement logic to end service log
		// Example: return serviceLogDAO.endServiceLog(logId);
		throw new UnsupportedOperationException("Not supported yet."); // Placeholder
	}
	
	/**
	 * Retrieves a list of all vehicles from the database.
	 * This method can be used by the frontend to populate dropdowns for vehicle selection.
	 *
	 * @return A list of {@link VehicleDTO} objects, or an empty list if an error occurs.
	 * @throws SQLException if a database access error occurs.
	 */
	public List<VehicleDTO> getAllVehicles() throws SQLException {
		return vehicleDAO.getAll(); // Assuming VehicleDAOImpl has an getAll() method
	}
	
	/**
	 * Closes the database connection.
	 * This method should be called when the application is shutting down to release
	 * database resources.
	 */
	public void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				LOGGER.info("GPSTrackingBusinessLogic connection closed.");
			}
		} catch (SQLException e) {
			LOGGER.severe("Error closing the database connection: " + e.getMessage());
		}
	}
}