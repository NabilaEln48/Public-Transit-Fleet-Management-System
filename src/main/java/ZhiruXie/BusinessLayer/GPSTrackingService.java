package ZhiruXie.BusinessLayer;

import ZhiruXie.DataAccessLayer.GPSTrackingDAOImpl;
import ZhiruXie.DataAccessLayer.VehicleDAOImp;
import ZhiruXie.DTO.GPSTrackingDTO;
import ZhiruXie.DTO.ServiceLogDTO;
import ZhiruXie.DTO.StationLogDTO;
import ZhiruXie.DTO.VehicleDTO;
import ZhiruXie.BusinessLayer.GPSLocationDTO;
import ZhiruXie.DataAccessLayer.GPSTrackingDAO;
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
 * Business Logic Service for GPS Tracking (FR-03)
 * Handles real-time location tracking and station arrival/departure logging
 *
 * @author Purnima
 */
public class GPSTrackingService {

    private static final Logger LOGGER = Logger.getLogger(GPSTrackingService.class.getName());
    private final GPSTrackingDAOImpl gpsDAO;
    private final VehicleDAOImp vehicleDAO;

    // GPS coordinate validation constants
    private static final double MIN_LATITUDE = -90.0;
    private static final double MAX_LATITUDE = 90.0;
    private static final double MIN_LONGITUDE = -180.0;
    private static final double MAX_LONGITUDE = 180.0;

    // Formatter for converting Timestamp to String
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public GPSTrackingService(Connection connection) {
        this.gpsDAO = new GPSTrackingDAOImpl(connection);
        this.vehicleDAO = new VehicleDAOImp(connection);
    }
    
    // Helper method to validate GPS coordinates
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
    
    // Helper method to validate station log data
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
     * Get the latest GPS data for a specific vehicle.
     * @param vehicleId Vehicle ID
     * @return GPSLocationDTO with the latest data
     */
    public GPSLocationDTO getLatestVehicleData(String vehicleId) {
        if (vehicleId == null || vehicleId.trim().isEmpty()) {
            LOGGER.warning("Attempted to get latest vehicle data with null or empty ID.");
            return null;
        }
        return gpsDAO.getCurrentLocation(vehicleId);
    }
    
    /**
     * Get the current location of a vehicle, in the format of GPSTrackingDTO.
     * This method is needed by the GPSTrackingServlet and handles the DTO mapping.
     * @param vehicleId The ID of the vehicle.
     * @return The current location as a GPSTrackingDTO, or null if not found.
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
     * Record real-time GPS location for a vehicle
     * @param vehicleId Vehicle ID
     * @param latitude GPS latitude
     * @param longitude GPS longitude
     * @param stationId Optional station ID if at station
     * @return ServiceResult indicating success/failure
     */
    public ServiceResult recordGPSLocation(String vehicleId, double latitude, double longitude, String stationId) {
        LOGGER.info("Recording GPS location for vehicle: " + vehicleId);

        ServiceResult validationResult = validateGPSData(vehicleId, latitude, longitude);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }

        VehicleDTO vehicle = null;
        try {
            vehicle = (VehicleDTO) vehicleDAO.getVehicleById(vehicleId);
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
     * Get current location of a vehicle
     * @param vehicleId Vehicle ID
     * @return GPSLocationDTO with current location or null if not found
     */
    public GPSLocationDTO getCurrentLocation(String vehicleId) {
        if (vehicleId == null || vehicleId.trim().isEmpty()) {
            LOGGER.warning("Invalid vehicle ID provided for location lookup");
            return null;
        }
        return gpsDAO.getCurrentLocation(vehicleId);
    }

    /**
     * Get location history for a vehicle within a date range
     * @param vehicleId Vehicle ID
     * @param startDate Start date
     * @param endDate End date
     * @return List of GPSLocationDTO
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
     * Get recent GPS data for all vehicles
     * @param limit Maximum number of records to return
     * @return List of recent GPS tracking data
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
     * Get all currently active vehicles (vehicles with recent GPS data)
     * @return List of active vehicles
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
     * Get vehicle tracking history by vehicle ID
     * @param vehicleId Vehicle ID as string
     * @return List of tracking history
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
     * Get vehicle tracking history with date range
     * @param vehicleId Vehicle ID as string
     * @param startDate Start date as string
     * @param endDate End date as string
     * @return List of tracking history
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
     * Get all GPS history within date range
     * @param startDate Start date as string
     * @param endDate End date as string
     * @return List of all GPS history
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
     * Get vehicle location report.
     * @param vehicleId Vehicle ID as string
     * @param startDate Start date as string
     * @param endDate End date as string
     * @return List of location reports
     */
    public List<GPSTrackingDTO> getVehicleLocationReport(String vehicleId, String startDate, String endDate) {
        return getVehicleTrackingHistory(vehicleId, startDate, endDate);
    }

    /**
     * Get all vehicles location report
     * @param startDate Start date as string
     * @param endDate End date as string
     * @return List of all vehicles location reports
     */
    public List<GPSTrackingDTO> getAllVehiclesLocationReport(String startDate, String endDate) {
        return getAllGPSHistory(startDate, endDate);
    }

    /**
     * Get breaks report for specified date range
     * @param startDate Start date as string
     * @param endDate End date as string
     * @return List of break reports
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
     * @param serviceLog The DTO containing the event details.
     * @return A ServiceResult indicating success or failure.
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
     * Logs a new GPS location event.
     * @param gpsEvent The DTO containing the GPS details.
     * @return A ServiceResult indicating success or failure.
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
     * Log vehicle arrival at station
     * @param vehicleId Vehicle ID
     * @param stationId Station ID
     * @return ServiceResult indicating success/failure
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
     * Log vehicle departure from station
     * @param vehicleId Vehicle ID
     * @param stationId Station ID
     * @return ServiceResult indicating success/failure
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
     * Logs the end of a break or out-of-service event.
     * This is the method that was missing.
     * * @param vehicleId The vehicle ID.
     * @param operatorId The operator ID.
     * @param logType The type of log, either "BREAK" or "OUT_OF_SERVICE".
     * @return A ServiceResult indicating success or failure.
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