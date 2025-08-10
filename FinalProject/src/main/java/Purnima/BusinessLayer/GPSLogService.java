/*
 * Assessment: Group Assignment
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Purnima Purnima
 * Student Id: 041161250
 * Professor Name: Teddy Yap
 */
package Purnima.BusinessLayer;

import Purnima.DataAccessLayer.GPSTrackingDAO;
import Purnima.DataAccessLayer.GPSTrackingDAOImpl;
import Purnima.DTO.GPSLogDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service class for GPS Log operations.
 * Handles business logic for GPS logging functionality, including validation
 * and data persistence. This class interacts with the database directly or
 * through a DAO layer.
 * Updated to match the actual database schema (live_tracking table).
 *
 * @author Your Name
 */
public class GPSLogService {
	
	/** The logger for this class. */
	private static final Logger LOGGER = Logger.getLogger(GPSLogService.class.getName());
	
	/** The database connection. */
	private Connection connection;
	
	/** The Data Access Object for GPS tracking. */
	private GPSTrackingDAO gpsDAO;
	
	/**
	 * Constructor with database connection.
	 * Initializes the service with a database connection and an associated DAO.
	 *
	 * @param connection The database connection to use.
	 */
	public GPSLogService(Connection connection) {
		this.connection = connection;
		this.gpsDAO = new GPSTrackingDAOImpl(connection);
	}
	
	/**
	 * Constructor with DAO injection (for existing code compatibility).
	 * This constructor allows for the injection of a specific DAO implementation,
	 * useful for testing or different persistence strategies.
	 *
	 * @param gpsTrackingDAO The GPS tracking DAO to use.
	 */
	public GPSLogService(GPSTrackingDAO gpsTrackingDAO) {
		this.gpsDAO = gpsTrackingDAO;
		// Note: connection will be null in this case, direct DAO methods only
	}
	
	/**
	 * Logs a GPS event to the database's live_tracking table.
	 * It performs validation on the provided GPS log data before attempting to save it.
	 *
	 * @param gpsLog The GPS log data to be saved.
	 * @return A {@link ServiceResult} indicating the success or failure of the operation.
	 */
	public ServiceResult logGPSEvent(GPSLogDTO gpsLog) {
		try {
			// Validation
			if (gpsLog.getVehicleId() == null || gpsLog.getVehicleId().trim().isEmpty()) {
				return new ServiceResult(false, "Vehicle ID is required");
			}
			
			if (gpsLog.getOperatorId() <= 0) {
				return new ServiceResult(false, "Valid operator ID is required");
			}
			
			// Validate coordinates if provided
			if (gpsLog.getLatitude() != null) {
				if (gpsLog.getLatitude() < -90 || gpsLog.getLatitude() > 90) {
					return new ServiceResult(false, "Invalid latitude. Must be between -90 and 90");
				}
			}
			
			if (gpsLog.getLongitude() != null) {
				if (gpsLog.getLongitude() < -180 || gpsLog.getLongitude() > 180) {
					return new ServiceResult(false, "Invalid longitude. Must be between -180 and 180");
				}
			}
			
			// Set timestamp if not provided
			if (gpsLog.getTimestamp() == null) {
				gpsLog.setTimestamp(new Timestamp(System.currentTimeMillis()));
			}
			
			// Insert GPS log into live_tracking table
			boolean success = insertGPSLogToLiveTracking(gpsLog);
			
			if (success) {
				LOGGER.info("GPS event logged successfully for vehicle: " + gpsLog.getVehicleId());
				return new ServiceResult(true, "GPS event logged successfully");
			} else {
				return new ServiceResult(false, "Failed to log GPS event to database");
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error logging GPS event", e);
			return new ServiceResult(false, "System error: " + e.getMessage());
		}
	}
	
	/**
	 * Retrieves a single GPS log entry by its ID from the live_tracking table.
	 * This method provides dummy data if no database connection is available for compatibility.
	 *
	 * @param logId The ID of the GPS log to retrieve.
	 * @return The {@link GPSLogDTO} if found, otherwise {@code null}.
	 */
	public GPSLogDTO getGPSLogById(int logId) {
		if (connection == null) {
			// Return dummy data for compatibility with existing code
			if (logId <= 0) {
				return null;
			}
			
			GPSLogDTO gpsLog = new GPSLogDTO();
			gpsLog.setId(logId);
			gpsLog.setVehicleId("TEST-001");
			gpsLog.setOperatorId(1);
			gpsLog.setLatitude(45.5017);
			gpsLog.setLongitude(-73.5673);
			gpsLog.setEventType("TEST");
			gpsLog.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
			gpsLog.setNotes("Test GPS log entry");
			return gpsLog;
		}
		
		String sql = "SELECT id, vehicle_ref, gps_lat, gps_lng, recorded_at, linked_station " +
				"FROM live_tracking WHERE id = ?";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, logId);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return mapLiveTrackingToGPSLogDTO(rs);
				}
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error retrieving GPS log by ID: " + logId, e);
		}
		return null;
	}
	
	/**
	 * Retrieves a list of all GPS logs with optional filtering from the live_tracking table.
	 * The results are ordered by the recording timestamp in descending order.
	 *
	 * @param dateFrom The start date filter (can be null).
	 * @param dateTo The end date filter (can be null).
	 * @param limit The maximum number of records to return.
	 * @return A {@link List} of {@link GPSLogDTO} objects. Returns an empty list if no connection is available.
	 */
	public List<GPSLogDTO> getAllGPSLogs(String dateFrom, String dateTo, int limit) {
		List<GPSLogDTO> gpsLogs = new ArrayList<>();
		
		if (connection == null) {
			return gpsLogs; // Return empty list if no connection
		}
		
		StringBuilder sql = new StringBuilder("SELECT id, vehicle_ref, gps_lat, gps_lng, recorded_at, linked_station " +
				"FROM live_tracking WHERE 1=1");
		
		List<Object> parameters = new ArrayList<>();
		
		if (dateFrom != null && !dateFrom.trim().isEmpty()) {
			sql.append(" AND recorded_at >= ?");
			try {
				parameters.add(Timestamp.valueOf(dateFrom + " 00:00:00"));
			} catch (IllegalArgumentException e) {
				LOGGER.warning("Invalid dateFrom format: " + dateFrom);
				return gpsLogs;
			}
		}
		
		if (dateTo != null && !dateTo.trim().isEmpty()) {
			sql.append(" AND recorded_at <= ?");
			try {
				parameters.add(Timestamp.valueOf(dateTo + " 23:59:59"));
			} catch (IllegalArgumentException e) {
				LOGGER.warning("Invalid dateTo format: " + dateTo);
				return gpsLogs;
			}
		}
		
		sql.append(" ORDER BY recorded_at DESC LIMIT ?");
		parameters.add(limit);
		
		try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
			for (int i = 0; i < parameters.size(); i++) {
				stmt.setObject(i + 1, parameters.get(i));
			}
			
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					gpsLogs.add(mapLiveTrackingToGPSLogDTO(rs));
				}
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error retrieving all GPS logs", e);
		}
		
		return gpsLogs;
	}
	
	/**
	 * Retrieves GPS logs for a specific vehicle from the live_tracking table.
	 *
	 * @param vehicleId The vehicle ID as a String.
	 * @param dateFrom The start date filter (can be null).
	 * @param dateTo The end date filter (can be null).
	 * @param limit The maximum number of records to return.
	 * @return A {@link List} of {@link GPSLogDTO} for the specified vehicle. Returns an empty list if no connection is available.
	 */
	public List<GPSLogDTO> getGPSLogsByVehicle(String vehicleId, String dateFrom, String dateTo, int limit) {
		List<GPSLogDTO> gpsLogs = new ArrayList<>();
		
		if (connection == null) {
			return gpsLogs; // Return empty list if no connection
		}
		
		StringBuilder sql = new StringBuilder("SELECT id, vehicle_ref, gps_lat, gps_lng, recorded_at, linked_station " +
				"FROM live_tracking WHERE vehicle_ref = ?");
		
		List<Object> parameters = new ArrayList<>();
		parameters.add(vehicleId);
		
		if (dateFrom != null && !dateFrom.trim().isEmpty()) {
			sql.append(" AND recorded_at >= ?");
			try {
				parameters.add(Timestamp.valueOf(dateFrom + " 00:00:00"));
			} catch (IllegalArgumentException e) {
				LOGGER.warning("Invalid dateFrom format: " + dateFrom);
				return gpsLogs;
			}
		}
		
		if (dateTo != null && !dateTo.trim().isEmpty()) {
			sql.append(" AND recorded_at <= ?");
			try {
				parameters.add(Timestamp.valueOf(dateTo + " 23:59:59"));
			} catch (IllegalArgumentException e) {
				LOGGER.warning("Invalid dateTo format: " + dateTo);
				return gpsLogs;
			}
		}
		
		sql.append(" ORDER BY recorded_at DESC LIMIT ?");
		parameters.add(limit);
		
		try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
			for (int i = 0; i < parameters.size(); i++) {
				stmt.setObject(i + 1, parameters.get(i));
			}
			
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					gpsLogs.add(mapLiveTrackingToGPSLogDTO(rs));
				}
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error retrieving GPS logs for vehicle: " + vehicleId, e);
		}
		
		return gpsLogs;
	}
	
	/**
	 * Bulk logs multiple GPS events from a single string.
	 * The input string is expected to be in a CSV-like format, with each line representing an event.
	 *
	 * @param bulkData A string containing multiple GPS events data, typically in CSV format.
	 * @return A {@link ServiceResult} indicating the overall success or failure of the bulk operation.
	 */
	public ServiceResult bulkLogGPSEvents(String bulkData) {
		try {
			if (bulkData == null || bulkData.trim().isEmpty()) {
				return new ServiceResult(false, "Bulk data is required");
			}
			
			String[] lines = bulkData.split("\n");
			int successCount = 0;
			int failCount = 0;
			List<String> errors = new ArrayList<>();
			
			for (int i = 0; i < lines.length; i++) {
				String line = lines[i].trim();
				if (line.isEmpty()) continue;
				
				try {
					GPSLogDTO gpsLog = parseBulkDataLine(line);
					ServiceResult result = logGPSEvent(gpsLog);
					
					if (result.isSuccess()) {
						successCount++;
					} else {
						failCount++;
						errors.add("Line " + (i + 1) + ": " + result.getMessage());
					}
				} catch (Exception e) {
					failCount++;
					errors.add("Line " + (i + 1) + ": " + e.getMessage());
				}
			}
			
			String message = String.format("Bulk operation completed: %d successful, %d failed",
					successCount, failCount);
			
			if (!errors.isEmpty() && errors.size() <= 5) {
				message += ". Errors: " + String.join("; ", errors);
			} else if (errors.size() > 5) {
				message += ". First 5 errors: " + String.join("; ", errors.subList(0, 5));
			}
			
			return new ServiceResult(failCount == 0, message);
			
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in bulk GPS logging", e);
			return new ServiceResult(false, "System error during bulk operation: " + e.getMessage());
		}
	}
	
	/**
	 * Helper method to parse a single line of bulk data into a GPSLogDTO object.
	 * The expected format is: vehicleId,latitude,longitude[,notes].
	 *
	 * @param line The string line to parse.
	 * @return A populated {@link GPSLogDTO} object.
	 * @throws Exception if the line format is invalid or parsing fails.
	 */
	private GPSLogDTO parseBulkDataLine(String line) throws Exception {
		String[] parts = line.split(",");
		
		if (parts.length < 3) {
			throw new IllegalArgumentException("Invalid bulk data format. Expected: vehicleId,latitude,longitude[,notes]");
		}
		
		GPSLogDTO gpsLog = new GPSLogDTO();
		gpsLog.setVehicleId(parts[0].trim());
		gpsLog.setOperatorId(1); // Default operator ID
		
		// Handle latitude
		if (!parts[1].trim().isEmpty() && !parts[1].trim().equalsIgnoreCase("null")) {
			gpsLog.setLatitude(Double.parseDouble(parts[1].trim()));
		}
		
		// Handle longitude
		if (!parts[2].trim().isEmpty() && !parts[2].trim().equalsIgnoreCase("null")) {
			gpsLog.setLongitude(Double.parseDouble(parts[2].trim()));
		}
		
		gpsLog.setEventType("BULK_IMPORT");
		gpsLog.setTimestamp(new Timestamp(System.currentTimeMillis()));
		
		// Handle optional notes
		if (parts.length > 3 && !parts[3].trim().isEmpty()) {
			gpsLog.setNotes(parts[3].trim());
		}
		
		return gpsLog;
	}
	
	/**
	 * Helper method to insert a GPS log into the live_tracking table.
	 * This method maps the fields from a GPSLogDTO to the corresponding columns in the database.
	 *
	 * @param gpsLog The GPS log DTO to insert.
	 * @return {@code true} if the insertion was successful, {@code false} otherwise.
	 * @throws SQLException if a database access error occurs.
	 */
	private boolean insertGPSLogToLiveTracking(GPSLogDTO gpsLog) throws SQLException {
		if (connection == null) {
			LOGGER.warning("No database connection available for GPS logging");
			return false;
		}
		
		String sql = "INSERT INTO live_tracking (vehicle_ref, gps_lat, gps_lng, recorded_at, linked_station) " +
				"VALUES (?, ?, ?, ?, ?)";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, gpsLog.getVehicleId());
			
			if (gpsLog.getLatitude() != null) {
				stmt.setDouble(2, gpsLog.getLatitude());
			} else {
				stmt.setNull(2, Types.DOUBLE);
			}
			
			if (gpsLog.getLongitude() != null) {
				stmt.setDouble(3, gpsLog.getLongitude());
			} else {
				stmt.setNull(3, Types.DOUBLE);
			}
			
			stmt.setTimestamp(4, gpsLog.getTimestamp());
			stmt.setString(5, gpsLog.getLinkedStation()); // Use linked_station field
			
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		}
	}
	
	/**
	 * Helper method to map a ResultSet from the live_tracking table to a GPSLogDTO object.
	 *
	 * @param rs The ResultSet containing the data from the database.
	 * @return A populated {@link GPSLogDTO} object.
	 * @throws SQLException if a database access error occurs.
	 */
	private GPSLogDTO mapLiveTrackingToGPSLogDTO(ResultSet rs) throws SQLException {
		GPSLogDTO gpsLog = new GPSLogDTO();
		gpsLog.setId(rs.getInt("id"));
		gpsLog.setVehicleId(rs.getString("vehicle_ref"));
		gpsLog.setOperatorId(1); // Default since live_tracking doesn't have operator_id
		gpsLog.setLatitude(rs.getObject("gps_lat", Double.class));
		gpsLog.setLongitude(rs.getObject("gps_lng", Double.class));
		gpsLog.setEventType("GPS_TRACKING"); // Default event type
		gpsLog.setTimestamp(rs.getTimestamp("recorded_at"));
		gpsLog.setLinkedStation(rs.getString("linked_station"));
		gpsLog.setNotes("Imported from live_tracking");
		
		return gpsLog;
	}
	
	/**
	 * Legacy method for compatibility with existing DAO-based code.
	 * Records a GPS location using the injected DAO.
	 *
	 * @param gpsLocation The GPS location DTO to record.
	 * @return {@code true} if the recording was successful, {@code false} otherwise.
	 */
	public boolean recordGPSLocation(GPSLocationDTO gpsLocation) {
		if (gpsDAO != null) {
			return gpsDAO.recordGPSLocation(gpsLocation);
		}
		return false;
	}
	
	/**
	 * Legacy method for compatibility with existing DAO-based code.
	 * Retrieves the current GPS location for a specific vehicle using the injected DAO.
	 *
	 * @param vehicleId The ID of the vehicle.
	 * @return The current {@link GPSLocationDTO} for the vehicle, or {@code null} if not found or no DAO is available.
	 */
	public GPSLocationDTO getCurrentLocation(String vehicleId) {
		if (gpsDAO != null) {
			return gpsDAO.getCurrentLocation(vehicleId);
		}
		return null;
	}
	
	/**
	 * Cleanup method to perform any necessary closing or resource management.
	 * This method logs a message indicating that cleanup is complete.
	 */
	public void cleanup() {
		LOGGER.info("GPSLogService cleanup completed");
	}
}