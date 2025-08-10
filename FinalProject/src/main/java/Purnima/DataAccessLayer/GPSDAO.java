
package Purnima.DataAccessLayer;



import Purnima.DTO.GPSDTO;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for GPS Tracking Management
 * Implements operations for GPS location tracking
 * 
 * @author Purnima
 */
public class GPSDAO {
    
    private Connection connection;
    
    public GPSDAO(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Create a new GPS log entry
     * @param gpsLog GPSLogDTO object containing GPS tracking information
     * @return true if GPS log was successfully created, false otherwise
     */
   public boolean createGPSLog(GPSDTO gpsLog) {
        String sql = "INSERT INTO live_tracking (vehicle_ref, gps_lat, gps_lng, recorded_at, linked_station) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, gpsLog.getVehicleRef());
            pstmt.setDouble(2, gpsLog.getGpsLat());
            pstmt.setDouble(3, gpsLog.getGpsLng());
            
            // CORRECTED LINE 39: Pass the Timestamp object directly
            pstmt.setTimestamp(4, gpsLog.getRecordedAt());
            
            pstmt.setString(5, gpsLog.getLinkedStation());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error creating GPS log: " + e.getMessage());
            return false;
        }
    }

    
    /**
     * Retrieve GPS logs for a specific vehicle
     * @param vehicleId the vehicle ID to search for
     * @return List of GPSLogDTO objects for the vehicle
     */
    public List<GPSDTO> getGPSLogsByVehicle(String vehicleId) {
        List<GPSDTO> gpsLogs = new ArrayList<>();
        String sql = "SELECT * FROM live_tracking WHERE vehicle_ref = ? ORDER BY recorded_at DESC";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, vehicleId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                gpsLogs.add(mapResultSetToGPSLog(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving GPS logs by vehicle: " + e.getMessage());
        }
        
        return gpsLogs;
    }
    
    /**
     * Get the latest GPS location for a vehicle
     * @param vehicleId the vehicle ID
     * @return GPSLogDTO object with latest location, null if not found
     */
    public GPSDTO getLatestGPSLog(String vehicleId) {
        String sql = "SELECT * FROM live_tracking WHERE vehicle_ref = ? ORDER BY recorded_at DESC LIMIT 1";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, vehicleId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToGPSLog(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving latest GPS log: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Get GPS logs within a time range
     * @param vehicleId the vehicle ID
     * @param startTime start of time range
     * @param endTime end of time range
     * @return List of GPSLogDTO objects within the time range
     */
    public List<GPSDTO> getGPSLogsByTimeRange(String vehicleId, LocalDateTime startTime, LocalDateTime endTime) {
        List<GPSDTO> gpsLogs = new ArrayList<>();
        String sql = "SELECT * FROM live_tracking WHERE vehicle_ref = ? AND recorded_at BETWEEN ? AND ? " +
                    "ORDER BY recorded_at ASC";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, vehicleId);
            pstmt.setTimestamp(2, Timestamp.valueOf(startTime));
            pstmt.setTimestamp(3, Timestamp.valueOf(endTime));
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                gpsLogs.add(mapResultSetToGPSLog(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving GPS logs by time range: " + e.getMessage());
        }
        
        return gpsLogs;
    }
    
    /**
     * Get all GPS logs for all vehicles (latest entries first)
     * @return List of all GPSLogDTO objects
     */
    public List<GPSDTO> getAllGPSLogs() {
        List<GPSDTO> gpsLogs = new ArrayList<>();
        String sql = "SELECT * FROM live_tracking ORDER BY recorded_at DESC";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                gpsLogs.add(mapResultSetToGPSLog(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving all GPS logs: " + e.getMessage());
        }
        
        return gpsLogs;
    }
    
    /**
     * Get GPS logs by station
     * @param stationId the station ID to filter by
     * @return List of GPSLogDTO objects for the station
     */
    public List<GPSDTO> getGPSLogsByStation(String stationId) {
        List<GPSDTO> gpsLogs = new ArrayList<>();
        String sql = "SELECT * FROM live_tracking WHERE linked_station = ? ORDER BY recorded_at DESC";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, stationId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                gpsLogs.add(mapResultSetToGPSLog(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving GPS logs by station: " + e.getMessage());
        }
        
        return gpsLogs;
    }
    
    /**
     * Delete old GPS logs (older than specified days)
     * @param daysOld number of days - logs older than this will be deleted
     * @return number of records deleted
     */
    public int deleteOldGPSLogs(int daysOld) {
        String sql = "DELETE FROM live_tracking WHERE recorded_at < DATE_SUB(NOW(), INTERVAL ? DAY)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, daysOld);
            return pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error deleting old GPS logs: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Map ResultSet to GPSLogDTO object
     * @param rs ResultSet from database query
     * @return GPSLogDTO object
     * @throws SQLException if error occurs while reading ResultSet
     */
     private GPSDTO mapResultSetToGPSLog(ResultSet rs) throws SQLException {
        GPSDTO gpsLog = new GPSDTO();
        gpsLog.setId(rs.getInt("id"));
        gpsLog.setVehicleRef(rs.getString("vehicle_ref"));
        gpsLog.setGpsLat(rs.getDouble("gps_lat"));
        gpsLog.setGpsLng(rs.getDouble("gps_lng"));
        
        // CORRECTION NEEDED HERE TOO: The DTO's setRecordedAt() method
        // should now accept a Timestamp, not a LocalDateTime.
        gpsLog.setRecordedAt(rs.getTimestamp("recorded_at"));
        
        gpsLog.setLinkedStation(rs.getString("linked_station"));
        return gpsLog;
    }
}
