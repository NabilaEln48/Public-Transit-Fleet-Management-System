
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

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * GPS Tracking DAO Interface
 * Defines a contract for GPS tracking data access operations.
 */
public interface GPSTrackingDAO {
    
    // Core CRUD and utility methods for GPSTrackingDTO
    List<GPSTrackingDTO> getAll() throws SQLException;
    GPSTrackingDTO getById(int id) throws SQLException;
    void add(GPSTrackingDTO dto) throws SQLException;
    void update(GPSTrackingDTO dto) throws SQLException;
    void delete(GPSTrackingDTO dto) throws SQLException;
    void printMetaData() throws SQLException;

    // Methods related to retrieving GPSTrackingDTO records
    List<GPSTrackingDTO> getByVehicleId(String vehicleId) throws SQLException;
    List<GPSTrackingDTO> getArrivalDepartureEvents() throws SQLException;
// Add this method to your GPSTrackingDAO interface
List<GPSLogDTO> getGPSLogsByVehicle(String vehicleId, String dateFrom, String dateTo, int limit) throws SQLException;

    // Original methods from your existing interface snippet
    boolean recordGPSLocation(GPSLocationDTO gpsLocation);
    GPSLocationDTO getCurrentLocation(String vehicleId);
    List<GPSLocationDTO> getLocationHistory(String vehicleId, String startDate, String endDate);
    boolean logStationEvent(StationLogDTO stationLog);
    List<StationLogDTO> getStationLogs(String vehicleId, String startDate, String endDate);
    boolean logServiceEvent(ServiceLogDTO serviceLog);
    boolean updateServiceLogEnd(String vehicleId, int operatorId, String logType);
    List<VehicleLocationSummary> getVehiclesOnRoute(String route);
    List<ServiceLogDTO> getActiveServiceLogs(String vehicleId);
    int deleteOldGPSRecords(Timestamp cutoffDate);
    
    // Additional methods that you already have implemented but need to be in the interface
    List<GPSTrackingDTO> getRecentGPSData(int limit) throws SQLException;
    List<GPSTrackingDTO> getActiveVehicles();
    List<GPSTrackingDTO> getVehicleTrackingHistory(String vehicleId);
    List<GPSTrackingDTO> getVehicleTrackingHistory(String vehicleId, String startDate, String endDate);
    List<GPSTrackingDTO> getAllGPSHistory(String startDate, String endDate);
    List<ServiceLogDTO> getBreaksReport(String startDate, String endDate);
    boolean logGPSEvent(GPSTrackingDTO gpsEvent);
    
    // New methods needed for your servlet
    GPSTrackingDTO getCurrentVehicleLocation(String vehicleId);
    List<GPSTrackingDTO> getVehicleLocationReport(String vehicleId, String startDate, String endDate);
    List<GPSTrackingDTO> getAllVehiclesLocationReport(String startDate, String endDate);
}