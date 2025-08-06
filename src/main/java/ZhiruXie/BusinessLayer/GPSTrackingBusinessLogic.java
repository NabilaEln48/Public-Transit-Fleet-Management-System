package ZhiruXie.BusinessLayer;

import ZhiruXie.DataAccessLayer.*;
import ZhiruXie.DTO.*;
import ZhiruXie.DataAccessLayer.DataSource;
import ZhiruXie.DataAccessLayer.LiveTrackingDAO;
import ZhiruXie.DataAccessLayer.LiveTrackingDAOImpl;
import ZhiruXie.DataAccessLayer.StationLogDAO;
import ZhiruXie.DataAccessLayer.StationLogDAOImpl;
import ZhiruXie.DataAccessLayer.VehicleDAOImp;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList; // Added import for ArrayList
import java.util.logging.Level; // Added import for Level
import java.util.logging.Logger; // Added import for Logger


public class GPSTrackingBusinessLogic {
    
    private static final Logger LOGGER = Logger.getLogger(GPSTrackingBusinessLogic.class.getName()); // Added Logger
    private final Connection connection; 
    
    private final LiveTrackingDAO liveTrackingDAO;
    private final StationLogDAO stationLogDAO;
    private final ServiceLogDAO serviceLogDAO;
    private final VehicleDAOImp vehicleDAO; // Added VehicleDAOImpl

    // Constructor now declares that it throws SQLException
    public GPSTrackingBusinessLogic() throws SQLException {
        // Attempt to get the database connection
        this.connection = DataSource.getConnection("cst8288", "cst8288");
        
        // IMPORTANT: Check if the connection is null. If DataSource.getConnection()
        // failed to establish a connection but didn't throw an exception,
        // this explicit check will ensure an SQLException is thrown.
        if (this.connection == null) {
            throw new SQLException("Failed to obtain a valid database connection for GPSTrackingBusinessLogic.");
        }
        
        // Pass the valid connection to the DAO constructors
        this.liveTrackingDAO = new LiveTrackingDAOImpl(connection);
        this.stationLogDAO = new StationLogDAOImpl(connection);
        this.serviceLogDAO = new ServiceLogDAOImpl(connection);
        this.vehicleDAO = new VehicleDAOImp(connection); // Initialize VehicleDAOImpl
    }
    
    /**
     * Get all live tracking data for managers
     * @return 
     */
    public List<LiveTrackingDTO> getAllLiveTracking() {
        // Implement logic to call liveTrackingDAO
        // Example: return liveTrackingDAO.getAllLiveTracking();
        throw new UnsupportedOperationException("Not supported yet."); // Placeholder
    }
    
    /**
     * Get tracking data for a specific vehicle
     */
    public List<LiveTrackingDTO> getVehicleTracking(String vehicleId) {
        // Implement logic to call liveTrackingDAO
        // Example: return liveTrackingDAO.getTrackingByVehicle(vehicleId);
        throw new UnsupportedOperationException("Not supported yet."); // Placeholder
    }
    
    /**
     * Get all station arrival/departure logs
     */
    public List<StationLogDTO> getAllStationLogs() {
        // Implement logic to call stationLogDAO
        // Example: return stationLogDAO.getAllStationLogs();
        throw new UnsupportedOperationException("Not supported yet."); // Placeholder
    }
    
    /**
     * Get station logs for a specific vehicle
     */
    public List<StationLogDTO> getVehicleStationLogs(String vehicleId) {
        // Implement logic to call stationLogDAO
        // Example: return stationLogDAO.getLogsByVehicle(vehicleId);
        throw new UnsupportedOperationException("Not supported yet."); // Placeholder
    }
    
    /**
     * Get service logs (breaks/out-of-service) for an operator
     */
    public List<ServiceLogDTO> getOperatorServiceLogs(int operatorId) {
        // Implement logic to call serviceLogDAO
        // Example: return serviceLogDAO.getLogsByOperator(operatorId);
        throw new UnsupportedOperationException("Not supported yet."); // Placeholder
    }
    
    /**
     * Record a new GPS location
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
     * Start a break or out-of-service period
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
     * End a service log
     */
    public boolean endServiceLog(int logId) {
        // Implement logic to end service log
        // Example: return serviceLogDAO.endServiceLog(logId);
        throw new UnsupportedOperationException("Not supported yet."); // Placeholder
    }

    /**
     * Retrieves a list of all vehicles.
     * This method can be used by the frontend to populate dropdowns for vehicle selection.
     * @return A list of VehicleDTO objects, or an empty list if an error occurs.
     */
    public List<VehicleDTO> getAllVehicles() throws SQLException {
        return vehicleDAO.getAll(); // Assuming VehicleDAOImpl has an getAll() method
    }
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("GPSTrackingBusinessLogic connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}