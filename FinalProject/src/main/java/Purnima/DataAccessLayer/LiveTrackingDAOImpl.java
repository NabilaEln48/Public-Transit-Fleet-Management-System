/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Purnima Purnima
 * Student Id: 041161250
 * Professor Name: Teddy Yap
 */

package Purnima.DataAccessLayer;

import Purnima.DTO.LiveTrackingDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of the {@link LiveTrackingDAO} interface for accessing
 * live vehicle tracking data from a database.
 * <p>
 * This class handles database operations for retrieving, and inserting
 * live tracking information by using a provided JDBC {@link Connection}.
 * </p>
 */
public class LiveTrackingDAOImpl implements LiveTrackingDAO {
    private Connection connection;
    
    /**
     * Constructs a new LiveTrackingDAOImpl with a database connection.
     *
     * @param connection The active {@link Connection} to the database.
     */
    public LiveTrackingDAOImpl(Connection connection) {
        this.connection = connection;
                
    }
    
    /**
     * Retrieves all live tracking records from the data source, joining with
     * the transit_vehicles table to get additional vehicle details.
     *
     * @return A list of all {@link LiveTrackingDTO} objects.
     */
    @Override
    public List<LiveTrackingDTO> getAllLiveTracking() {
        List<LiveTrackingDTO> trackingList = new ArrayList<>();
        String sql = """
            SELECT lt.id, lt.vehicle_ref, lt.gps_lat, lt.gps_lng, 
                   lt.recorded_at, lt.linked_station,
                   tv.registration_number, tv.category
            FROM live_tracking lt
            JOIN transit_vehicles tv ON lt.vehicle_ref = tv.id
            ORDER BY lt.recorded_at DESC
            """;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                LiveTrackingDTO tracking = new LiveTrackingDTO();
                tracking.setId(rs.getInt("id"));
                tracking.setVehicleRef(rs.getString("vehicle_ref"));
                tracking.setGpsLat(rs.getDouble("gps_lat"));
                tracking.setGpsLng(rs.getDouble("gps_lng"));
                tracking.setRecordedAt(rs.getTimestamp("recorded_at"));
                tracking.setLinkedStation(rs.getString("linked_station"));
                tracking.setVehicleRegistration(rs.getString("registration_number"));
                tracking.setVehicleCategory(rs.getString("category"));
                trackingList.add(tracking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trackingList;
    }
    
    /**
     * Retrieves all live tracking records for a specific vehicle.
     *
     * @param vehicleId The unique identifier of the vehicle.
     * @return A list of {@link LiveTrackingDTO} objects for the specified vehicle.
     */
    @Override
    public List<LiveTrackingDTO> getTrackingByVehicle(String vehicleId) {
        List<LiveTrackingDTO> trackingList = new ArrayList<>();
        String sql = """
            SELECT lt.id, lt.vehicle_ref, lt.gps_lat, lt.gps_lng, 
                   lt.recorded_at, lt.linked_station,
                   tv.registration_number, tv.category
            FROM live_tracking lt
            JOIN transit_vehicles tv ON lt.vehicle_ref = tv.id
            WHERE lt.vehicle_ref = ?
            ORDER BY lt.recorded_at DESC
            """;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vehicleId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LiveTrackingDTO tracking = new LiveTrackingDTO();
                    tracking.setId(rs.getInt("id"));
                    tracking.setVehicleRef(rs.getString("vehicle_ref"));
                    tracking.setGpsLat(rs.getDouble("gps_lat"));
                    tracking.setGpsLng(rs.getDouble("gps_lng"));
                    tracking.setRecordedAt(rs.getTimestamp("recorded_at"));
                    tracking.setLinkedStation(rs.getString("linked_station"));
                    tracking.setVehicleRegistration(rs.getString("registration_number"));
                    tracking.setVehicleCategory(rs.getString("category"));
                    trackingList.add(tracking);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trackingList;
    }
    
    /**
     * Inserts a new live tracking record into the data source.
     *
     * @param tracking The {@link LiveTrackingDTO} object containing the tracking data to be inserted.
     * @return {@code true} if the insertion was successful, {@code false} otherwise.
     */
    @Override
    public boolean insertTrackingData(LiveTrackingDTO tracking) {
        String sql = """
            INSERT INTO live_tracking (vehicle_ref, gps_lat, gps_lng, recorded_at, linked_station)
            VALUES (?, ?, ?, ?, ?)
            """;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tracking.getVehicleRef());
            stmt.setDouble(2, tracking.getGpsLat());
            stmt.setDouble(3, tracking.getGpsLng());
            stmt.setTimestamp(4,tracking.getRecordedAt());
            stmt.setString(5, tracking.getLinkedStation());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Retrieves the most recent live tracking record for a specific vehicle.
     *
     * @param vehicleId The unique identifier of the vehicle.
     * @return The latest {@link LiveTrackingDTO} object for the specified vehicle, or {@code null} if none is found.
     */
    @Override
    public LiveTrackingDTO getLatestTrackingByVehicle(String vehicleId) {
        String sql = """
            SELECT lt.id, lt.vehicle_ref, lt.gps_lat, lt.gps_lng, 
                   lt.recorded_at, lt.linked_station,
                   tv.registration_number, tv.category
            FROM live_tracking lt
            JOIN transit_vehicles tv ON lt.vehicle_ref = tv.id
            WHERE lt.vehicle_ref = ?
            ORDER BY lt.recorded_at DESC
            LIMIT 1
            """;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vehicleId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LiveTrackingDTO tracking = new LiveTrackingDTO();
                    tracking.setId(rs.getInt("id"));
                    tracking.setVehicleRef(rs.getString("vehicle_ref"));
                    tracking.setGpsLat(rs.getDouble("gps_lat"));
                    tracking.setGpsLng(rs.getDouble("gps_lng"));
                    tracking.setRecordedAt(rs.getTimestamp("recorded_at"));
                    tracking.setLinkedStation(rs.getString("linked_station"));
                    tracking.setVehicleRegistration(rs.getString("registration_number"));
                    tracking.setVehicleCategory(rs.getString("category"));
                    return tracking;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}