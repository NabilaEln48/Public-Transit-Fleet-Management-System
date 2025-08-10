/*
 * Assessment: Project Group
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

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GPS tracking DAO implementation.
 * <p>
 * This implementation uses the actual database tables: 'live_tracking' and 'station_logs'.
 * It provides a concrete way to interact with the database to perform data access
 * operations for GPS tracking and related events.
 * </p>
 */
public class GPSTrackingDAOImpl implements GPSTrackingDAO {

	private final Connection connection;

	/**
	 * Constructor to initialize the DAO with a database connection.
	 *
	 * @param connection The active {@link Connection} to the database.
	 */
	public GPSTrackingDAOImpl(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Gets all GPS tracking records from the live_tracking table.
	 * <p>
	 * Note: This method only retrieves the fields available in the 'live_tracking' table.
	 * </p>
	 *
	 * @return A list of {@link GPSTrackingDTO} with limited data.
	 * @throws SQLException if a database error occurs.
	 */
	@Override
	public List<GPSTrackingDTO> getAll() throws SQLException {
		List<GPSTrackingDTO> list = new ArrayList<>();
		String query = "SELECT id, vehicle_ref, gps_lat, gps_lng, recorded_at, linked_station FROM live_tracking ORDER BY recorded_at DESC";

		try (PreparedStatement stmt = connection.prepareStatement(query);
			 ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				GPSTrackingDTO dto = new GPSTrackingDTO();
				dto.setTrackingId(rs.getInt("id"));
				dto.setVehicleId(rs.getString("vehicle_ref"));
				dto.setGpsLat(rs.getDouble("gps_lat"));
				dto.setGpsLng(rs.getDouble("gps_lng"));
				dto.setRecordedAt(rs.getTimestamp("recorded_at"));
				dto.setLinkedStation(rs.getString("linked_station"));
				list.add(dto);
			}
		}
		return list;
	}

	/**
	 * Gets a GPS tracking record by id from the live_tracking table.
	 * <p>
	 * Note: This method only retrieves the fields available in the 'live_tracking' table.
	 * </p>
	 *
	 * @param id The tracking id.
	 * @return A {@link GPSTrackingDTO} with limited data, or {@code null} if no record is found.
	 * @throws SQLException if a database error occurs.
	 */
	@Override
	public GPSTrackingDTO getById(int id) throws SQLException {
		GPSTrackingDTO dto = null;
		String query = "SELECT id, vehicle_ref, gps_lat, gps_lng, recorded_at, linked_station FROM live_tracking WHERE id = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					dto = new GPSTrackingDTO();
					dto.setTrackingId(rs.getInt("id"));
					dto.setVehicleId(rs.getString("vehicle_ref"));
					dto.setGpsLat(rs.getDouble("gps_lat"));
					dto.setGpsLng(rs.getDouble("gps_lng"));
					dto.setRecordedAt(rs.getTimestamp("recorded_at"));
					dto.setLinkedStation(rs.getString("linked_station"));
				}
			}
		}
		return dto;
	}

	/**
	 * Adds a new GPS tracking record.
	 * <p>
	 * This method is not supported for the live_tracking table and will throw an exception.
	 * Use {@link #logGPSEvent(GPSTrackingDTO)} instead.
	 * </p>
	 * @param dto The {@link GPSTrackingDTO} to be added.
	 * @throws UnsupportedOperationException as this method is not supported.
	 */
	@Override
	public void add(GPSTrackingDTO dto) throws SQLException {
		throw new UnsupportedOperationException("The generic 'add' method is not supported for the 'live_tracking' table. Use 'logGPSEvent' instead.");
	}
	
	/**
	 * Updates an existing GPS tracking record.
	 * <p>
	 * This method is not supported for the live_tracking table as records are considered immutable.
	 * </p>
	 * @param dto The {@link GPSTrackingDTO} to be updated.
	 * @throws UnsupportedOperationException as this method is not supported.
	 */
	@Override
	public void update(GPSTrackingDTO dto) throws SQLException {
		throw new UnsupportedOperationException("The 'update' method is not supported for the 'live_tracking' table.");
	}

	/**
	 * Deletes a GPS tracking record.
	 * <p>
	 * This method is not supported for the live_tracking table as records are historical.
	 * Use a dedicated method for purging old records like {@link #deleteOldGPSRecords(Timestamp)}.
	 * </p>
	 * @param dto The {@link GPSTrackingDTO} to be deleted.
	 * @throws UnsupportedOperationException as this method is not supported.
	 */
	@Override
	public void delete(GPSTrackingDTO dto) throws SQLException {
		throw new UnsupportedOperationException("The 'delete' method is not supported for the 'live_tracking' table.");
	}

	/**
	 * Logs a new GPS event into the 'live_tracking' table.
	 *
	 * @param gpsEvent The {@link GPSTrackingDTO} containing the GPS event data.
	 * @return {@code true} if the record was successfully inserted, {@code false} otherwise.
	 */
	@Override
	public boolean logGPSEvent(GPSTrackingDTO gpsEvent) {
		String sql = "INSERT INTO live_tracking (vehicle_ref, gps_lat, gps_lng, recorded_at, linked_station) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, gpsEvent.getVehicleId());
			stmt.setDouble(2, gpsEvent.getGpsLat());
			stmt.setDouble(3, gpsEvent.getGpsLng());
			stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			if (gpsEvent.getLinkedStation() != null && !gpsEvent.getLinkedStation().isEmpty()) {
				stmt.setString(5, gpsEvent.getLinkedStation());
			} else {
				stmt.setNull(5, Types.VARCHAR);
			}
			
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			System.err.println("Error inserting GPS tracking data into live_tracking: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Logs a new station event into the 'station_logs' table.
	 *
	 * @param stationLog The {@link StationLogDTO} containing the station event data.
	 * @return {@code true} if the log was successful, {@code false} otherwise.
	 */
	@Override
	public boolean logStationEvent(StationLogDTO stationLog) {
		String sql = "INSERT INTO station_logs (vehicle_ref, station_ref, action_type, logged_time) VALUES (?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, stationLog.getVehicleRef());
			stmt.setString(2, stationLog.getStationRef());
			stmt.setString(3, stationLog.getActionType());
			stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			System.err.println("Error inserting station log: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Logs a new break or out-of-service event into the 'service_logs' table.
	 *
	 * @param serviceLog The {@link ServiceLogDTO} with event details.
	 * @return {@code true} if the log was successful.
	 */
	@Override
	public boolean logServiceEvent(ServiceLogDTO serviceLog) {
		String sql = "INSERT INTO service_logs (vehicle_ref, break_start, log_type, operator_ref) VALUES (?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, serviceLog.getVehicleRef());
			stmt.setTimestamp(2, serviceLog.getBreakStart());
			stmt.setString(3, serviceLog.getLogType());	
			stmt.setInt(4, serviceLog.getOperatorRef());
			
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			System.err.println("Error inserting service log: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Updates an existing break or out-of-service log with the end time.
	 *
	 * @param vehicleId The vehicle identifier.
	 * @param operatorId The operator identifier.
	 * @param logType The type of log ('Break' or 'OutOfService').
	 * @return {@code true} if the update was successful.
	 */
	@Override
	public boolean updateServiceLogEnd(String vehicleId, int operatorId, String logType) {
		String sql = "UPDATE service_logs SET break_end = ? WHERE vehicle_ref = ? AND operator_ref = ? AND log_type = ? AND break_end IS NULL ORDER BY break_start DESC LIMIT 1";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			stmt.setString(2, vehicleId);
			stmt.setInt(3, operatorId);
			stmt.setString(4, logType);	
			
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			System.err.println("Error updating service log end time: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Retrieves all GPS tracking records for a specific vehicle.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @return A {@code List} of {@link GPSTrackingDTO} records for the specified vehicle.
	 * @throws SQLException if a database access error occurs.
	 */
	@Override
	public List<GPSTrackingDTO> getByVehicleId(String vehicleId) throws SQLException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Retrieves all arrival and departure events from the data source.
	 *
	 * @return A {@code List} of {@link GPSTrackingDTO} records representing arrival and departure events.
	 * @throws SQLException if a database access error occurs.
	 */
	@Override public List<GPSTrackingDTO> getArrivalDepartureEvents() throws SQLException { throw new UnsupportedOperationException("Not supported yet."); }

	/**
	 * Retrieves a limited number of GPS logs for a specific vehicle within a given date range.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @param dateFrom The start date for the log query (inclusive), in a format understandable by the database.
	 * @param dateTo The end date for the log query (inclusive), in a format understandable by the database.
	 * @param limit The maximum number of records to retrieve.
	 * @return A {@code List} of {@link GPSLogDTO} records.
	 * @throws SQLException if a database access error occurs.
	 */
	@Override public List<GPSLogDTO> getGPSLogsByVehicle(String vehicleId, String dateFrom, String dateTo, int limit) throws SQLException { throw new UnsupportedOperationException("Not supported yet."); }

	/**
	 * Records a new GPS location for a vehicle.
	 *
	 * @param gpsLocation The {@link GPSLocationDTO} containing the location data.
	 * @return {@code true} if the recording was successful, {@code false} otherwise.
	 */
	@Override public boolean recordGPSLocation(GPSLocationDTO gpsLocation) { throw new UnsupportedOperationException("Not supported yet."); }

	/**
	 * Retrieves the current location of a specific vehicle.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @return The {@link GPSLocationDTO} for the vehicle's current location.
	 */
	@Override public GPSLocationDTO getCurrentLocation(String vehicleId) { throw new UnsupportedOperationException("Not supported yet."); }

	/**
	 * Retrieves the location history for a specific vehicle within a date range.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @param startDate The start date of the history.
	 * @param endDate The end date of the history.
	 * @return A {@code List} of {@link GPSLocationDTO} records.
	 */
	@Override public List<GPSLocationDTO> getLocationHistory(String vehicleId, String startDate, String endDate) { throw new UnsupportedOperationException("Not supported yet."); }

	/**
	 * Retrieves station log events for a vehicle within a date range.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @param startDate The start date for the logs.
	 * @param endDate The end date for the logs.
	 * @return A {@code List} of {@link StationLogDTO} records.
	 */
	@Override public List<StationLogDTO> getStationLogs(String vehicleId, String startDate, String endDate) { throw new UnsupportedOperationException("Not supported yet."); }

	/**
	 * Retrieves a summary of all vehicles currently on a specific route.
	 *
	 * @param route The ID or name of the route.
	 * @return A {@code List} of {@link VehicleLocationSummary} objects.
	 */
	@Override public List<VehicleLocationSummary> getVehiclesOnRoute(String route) { throw new UnsupportedOperationException("Not supported yet."); }

	/**
	 * Retrieves all active service logs for a specific vehicle.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @return A {@code List} of {@link ServiceLogDTO} records.
	 */
	@Override public List<ServiceLogDTO> getActiveServiceLogs(String vehicleId) { throw new UnsupportedOperationException("Not supported yet."); }

	/**
	 * Deletes GPS records that are older than a specified cutoff date.
	 *
	 * @param cutoffDate The timestamp before which records should be deleted.
	 * @return The number of records deleted.
	 */
	@Override public int deleteOldGPSRecords(Timestamp cutoffDate) { throw new UnsupportedOperationException("Not supported yet."); }

	/**
	 * Retrieves a limited number of the most recent GPS data records.
	 *
	 * @param limit The maximum number of records to retrieve.
	 * @return A {@code List} of recent {@link GPSTrackingDTO} records.
	 * @throws SQLException if a database access error occurs.
	 */
	@Override public List<GPSTrackingDTO> getRecentGPSData(int limit) throws SQLException { throw new UnsupportedOperationException("Not supported yet."); }

	/**
	 * Retrieves a list of all vehicles that are currently active.
	 *
	 * @return A {@code List} of {@link GPSTrackingDTO} records for active vehicles.
	 */
	@Override public List<GPSTrackingDTO> getActiveVehicles() { throw new UnsupportedOperationException("Not supported yet."); }

	/**
	 * Retrieves the full tracking history for a specific vehicle.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @return A {@code List} of all {@link GPSTrackingDTO} records for the vehicle.
	 */
	@Override public List<GPSTrackingDTO> getVehicleTrackingHistory(String vehicleId) { throw new UnsupportedOperationException("Not supported yet."); }

	/**
	 * Retrieves the tracking history for a specific vehicle within a date range.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @param startDate The start date of the history.
	 * @param endDate The end date of the history.
	 * @return A {@code List} of {@link GPSTrackingDTO} records.
	 */
	@Override public List<GPSTrackingDTO> getVehicleTrackingHistory(String vehicleId, String startDate, String endDate) { throw new UnsupportedOperationException("Not supported yet."); }

	/**
	 * Retrieves all GPS history records within a specific date range.
	 *
	 * @param startDate The start date of the history.
	 * @param endDate The end date of the history.
	 * @return A {@code List} of all {@link GPSTrackingDTO} records.
	 */
	@Override public List<GPSTrackingDTO> getAllGPSHistory(String startDate, String endDate) { throw new UnsupportedOperationException("Not supported yet."); }

	/**
	 * Retrieves a report on service breaks for all vehicles within a date range.
	 *
	 * @param startDate The start date of the report period.
	 * @param endDate The end date of the report period.
	 * @return A {@code List} of {@link ServiceLogDTO} records representing breaks.
	 */
	@Override public List<ServiceLogDTO> getBreaksReport(String startDate, String endDate) { throw new UnsupportedOperationException("Not supported yet."); }

	/**
	 * Retrieves the most recent location of a specific vehicle.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @return The current {@link GPSTrackingDTO} for the vehicle.
	 */
	@Override public GPSTrackingDTO getCurrentVehicleLocation(String vehicleId) { throw new UnsupportedOperationException("Not supported yet."); }

	/**
	 * Retrieves a detailed location report for a single vehicle within a date range.
	 *
	 * @param vehicleId The unique ID of the vehicle.
	 * @param startDate The start date for the report.
	 * @param endDate The end date for the report.
	 * @return A {@code List} of {@link GPSTrackingDTO} records for the report.
	 */
	@Override public List<GPSTrackingDTO> getVehicleLocationReport(String vehicleId, String startDate, String endDate) { throw new UnsupportedOperationException("Not supported yet."); }

	/**
	 * Retrieves a detailed location report for all vehicles within a date range.
	 *
	 * @param startDate The start date for the report.
	 * @param endDate The end date for the report.
	 * @return A {@code List} of all {@link GPSTrackingDTO} records for the report.
	 */
	@Override public List<GPSTrackingDTO> getAllVehiclesLocationReport(String startDate, String endDate) { throw new UnsupportedOperationException("Not supported yet."); }

    @Override
    public void printMetaData() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
