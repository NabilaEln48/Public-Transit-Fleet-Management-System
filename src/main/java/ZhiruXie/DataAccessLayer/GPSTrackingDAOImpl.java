/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Purnima Purnima
 * Student Id: 041161250
 * Professor Name: Teddy Yap
 */



package ZhiruXie.DataAccessLayer;

import ZhiruXie.BusinessLayer.GPSLocationDTO;
import ZhiruXie.BusinessLayer.VehicleLocationSummary;
import ZhiruXie.DTO.GPSLogDTO;
import ZhiruXie.DTO.GPSTrackingDTO;
import ZhiruXie.DTO.ServiceLogDTO;
import ZhiruXie.DTO.StationLogDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

/**
 * GPS tracking DAO implementation.
 * This implementation uses the actual database tables: 'live_tracking' and 'station_logs'.
 */
public class GPSTrackingDAOImpl implements GPSTrackingDAO {

    private final Connection connection;

    public GPSTrackingDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Gets all GPS tracking records from the live_tracking table.
     * Note: This method only retrieves the fields available in the 'live_tracking' table.
     *
     * @return list of GPSTrackingDTO with limited data.
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
     * Note: This method only retrieves the fields available in the 'live_tracking' table.
     *
     * @param id tracking id
     * @return GPS tracking dto with limited data
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

    @Override
    public void add(GPSTrackingDTO dto) throws SQLException {
        throw new UnsupportedOperationException("The generic 'add' method is not supported for the 'live_tracking' table. Use 'logGPSEvent' instead.");
    }
    
    @Override
    public void update(GPSTrackingDTO dto) throws SQLException {
        throw new UnsupportedOperationException("The 'update' method is not supported for the 'live_tracking' table.");
    }

    @Override
    public void delete(GPSTrackingDTO dto) throws SQLException {
        throw new UnsupportedOperationException("The 'delete' method is not supported for the 'live_tracking' table.");
    }

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
     * This is the corrected implementation with the 'notes' field removed from the query.
     *
     * @param serviceLog The ServiceLogDTO with event details.
     * @return true if the log was successful.
     */
    @Override
    public boolean logServiceEvent(ServiceLogDTO serviceLog) {
        // Corrected SQL query: 'notes' column removed
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
     * This is the correct implementation for your 'logBreakEnd' action.
     *
     * @param vehicleId The vehicle identifier.
     * @param operatorId The operator identifier.
     * @param logType The type of log ('Break' or 'OutOfService').
     * @return true if the update was successful.
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
    
    // ================================================================
    // DUMMY IMPLEMENTATIONS (You need to implement these when needed)
    // ================================================================

    @Override public void printMetaData() throws SQLException { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public List<GPSLogDTO> getGPSLogsByVehicle(String vehicleId, String dateFrom, String dateTo, int limit) throws SQLException { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public List<GPSTrackingDTO> getByVehicleId(String vehicleId) throws SQLException { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public List<GPSTrackingDTO> getArrivalDepartureEvents() throws SQLException { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public boolean recordGPSLocation(GPSLocationDTO gpsLocation) { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public GPSLocationDTO getCurrentLocation(String vehicleId) { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public List<GPSLocationDTO> getLocationHistory(String vehicleId, String startDate, String endDate) { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public List<StationLogDTO> getStationLogs(String vehicleId, String startDate, String endDate) { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public List<VehicleLocationSummary> getVehiclesOnRoute(String route) { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public List<ServiceLogDTO> getActiveServiceLogs(String vehicleId) { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public int deleteOldGPSRecords(Timestamp cutoffDate) { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public List<GPSTrackingDTO> getRecentGPSData(int limit) throws SQLException { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public List<GPSTrackingDTO> getActiveVehicles() { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public List<GPSTrackingDTO> getVehicleTrackingHistory(String vehicleId) { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public List<GPSTrackingDTO> getVehicleTrackingHistory(String vehicleId, String startDate, String endDate) { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public List<GPSTrackingDTO> getAllGPSHistory(String startDate, String endDate) { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public List<ServiceLogDTO> getBreaksReport(String startDate, String endDate) { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public GPSTrackingDTO getCurrentVehicleLocation(String vehicleId) { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public List<GPSTrackingDTO> getVehicleLocationReport(String vehicleId, String startDate, String endDate) { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public List<GPSTrackingDTO> getAllVehiclesLocationReport(String startDate, String endDate) { throw new UnsupportedOperationException("Not supported yet."); }
}