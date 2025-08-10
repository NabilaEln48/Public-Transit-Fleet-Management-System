/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Purnima Purnima
 * Student Id: 041161250
 * Professor Name: Teddy Yap
 */

package Purnima.DataAccessLayer;

import Purnima.DTO.StationLogDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of the {@link StationLogDAO} interface for accessing
 * station log data from a database.
 * <p>
 * This class handles database operations for retrieving and inserting
 * station log information, such as arrivals and departures.
 * </p>
 */
public class StationLogDAOImpl implements StationLogDAO {
    private Connection connection;
    
    /**
     * Constructs a new StationLogDAOImpl with a database connection.
     *
     * @param connection The active {@link Connection} to the database.
     */
    public StationLogDAOImpl(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Retrieves all station log records from the data source.
     * This method joins the `station_logs`, `route_stations`, and `transit_vehicles` tables
     * to provide a comprehensive view of all station logs.
     *
     * @return A list of all {@link StationLogDTO} objects representing station logs.
     */
    @Override
    public List<StationLogDTO> getAllStationLogs() {
        List<StationLogDTO> logList = new ArrayList<>();
        String sql = """
            SELECT sl.id, sl.vehicle_ref, sl.station_ref, sl.action_type, sl.logged_time,
                   rs.station_name, tv.registration_number
            FROM station_logs sl
            JOIN route_stations rs ON sl.station_ref = rs.id
            JOIN transit_vehicles tv ON sl.vehicle_ref = tv.id
            ORDER BY sl.logged_time DESC
            """;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                StationLogDTO log = new StationLogDTO();
                log.setId(rs.getInt("id"));
                log.setVehicleRef(rs.getString("vehicle_ref"));
                log.setStationRef(rs.getString("station_ref"));
                log.setActionType(rs.getString("action_type"));
                log.setLoggedTime(rs.getTimestamp("logged_time"));
                log.setStationName(rs.getString("station_name"));
                log.setVehicleRegistration(rs.getString("registration_number"));
                logList.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logList;
    }
    
    /**
     * Retrieves all station log records for a specific vehicle.
     *
     * @param vehicleId The unique identifier of the vehicle.
     * @return A list of {@link StationLogDTO} objects for the specified vehicle.
     */
    @Override
    public List<StationLogDTO> getLogsByVehicle(String vehicleId) {
        List<StationLogDTO> logList = new ArrayList<>();
        String sql = """
            SELECT sl.id, sl.vehicle_ref, sl.station_ref, sl.action_type, sl.logged_time,
                   rs.station_name, tv.registration_number
            FROM station_logs sl
            JOIN route_stations rs ON sl.station_ref = rs.id
            JOIN transit_vehicles tv ON sl.vehicle_ref = tv.id
            WHERE sl.vehicle_ref = ?
            ORDER BY sl.logged_time DESC
            """;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vehicleId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    StationLogDTO log = new StationLogDTO();
                    log.setId(rs.getInt("id"));
                    log.setVehicleRef(rs.getString("vehicle_ref"));
                    log.setStationRef(rs.getString("station_ref"));
                    log.setActionType(rs.getString("action_type"));
                    log.setLoggedTime(rs.getTimestamp("logged_time"));
                    log.setStationName(rs.getString("station_name"));
                    log.setVehicleRegistration(rs.getString("registration_number"));
                    logList.add(log);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logList;
    }
    
    /**
     * Inserts a new station log record into the data source.
     *
     * @param stationLog The {@link StationLogDTO} object containing the station log data to be inserted.
     * @return {@code true} if the insertion was successful, {@code false} otherwise.
     */
    @Override
    public boolean insertStationLog(StationLogDTO stationLog) {
        String sql = """
            INSERT INTO station_logs (vehicle_ref, station_ref, action_type, logged_time)
            VALUES (?, ?, ?, ?)
            """;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, stationLog.getVehicleRef());
            stmt.setString(2, stationLog.getStationRef());
            stmt.setString(3, stationLog.getActionType());
            stmt.setTimestamp(4, stationLog.getLoggedTime());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

