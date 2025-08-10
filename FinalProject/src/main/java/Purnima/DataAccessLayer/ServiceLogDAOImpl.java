/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Purnima Purnima
 * Student Id: 041161250
 * Professor Name: Teddy Yap
 */

package Purnima.DataAccessLayer;

import Purnima.DTO.ServiceLogDTO;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of the {@link ServiceLogDAO} interface for accessing
 * service log data from a database.
 * <p>
 * This class handles database operations for retrieving, inserting, and
 * updating service logs, such as breaks or out-of-service events.
 * </p>
 */
public class ServiceLogDAOImpl implements ServiceLogDAO {
    private Connection connection;
    
    /**
     * Constructs a new ServiceLogDAOImpl with a database connection.
     *
     * @param connection The active {@link Connection} to the database.
     */
    public ServiceLogDAOImpl(Connection connection) {
      this.connection= connection ;
    }
    
    /**
     * Retrieves all service log records from the data source.
     * This method joins the `service_logs`, `user`, and `transit_vehicles` tables
     * to provide a comprehensive view of all service logs.
     *
     * @return A list of all {@link ServiceLogDTO} objects representing service logs.
     */
    @Override
    public List<ServiceLogDTO> getAllServiceLogs() {
        List<ServiceLogDTO> logList = new ArrayList<>();
        String sql = """
            SELECT sl.id, sl.vehicle_ref, sl.break_start, sl.break_end, 
                   sl.log_type, sl.operator_ref,
                   u.name as operator_name, tv.registration_number
            FROM service_logs sl
            JOIN user u ON sl.operator_ref = u.id
            JOIN transit_vehicles tv ON sl.vehicle_ref = tv.id
            ORDER BY sl.break_start DESC
            """;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                ServiceLogDTO log = new ServiceLogDTO();
                log.setId(rs.getInt("id"));
                log.setVehicleRef(rs.getString("vehicle_ref"));
                
                Timestamp startTimestamp = rs.getTimestamp("break_start");
                if (startTimestamp != null) {
                    log.setBreakStart(startTimestamp);
                }
                
                Timestamp endTimestamp = rs.getTimestamp("break_end");
                if (endTimestamp != null) {
                    log.setBreakEnd(endTimestamp);
                }
                
                log.setLogType(rs.getString("log_type"));
                log.setOperatorRef(rs.getInt("operator_ref"));
                log.setOperatorName(rs.getString("operator_name"));
                log.setVehicleRegistration(rs.getString("registration_number"));
                logList.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logList;
    }
    
    /**
     * Retrieves all service log records for a specific operator.
     *
     * @param operatorId The unique identifier of the operator.
     * @return A list of {@link ServiceLogDTO} objects for the specified operator.
     */
    @Override
    public List<ServiceLogDTO> getLogsByOperator(int operatorId) {
        List<ServiceLogDTO> logList = new ArrayList<>();
        String sql = """
            SELECT sl.id, sl.vehicle_ref, sl.break_start, sl.break_end, 
                   sl.log_type, sl.operator_ref,
                   u.name as operator_name, tv.registration_number
            FROM service_logs sl
            JOIN user u ON sl.operator_ref = u.id
            JOIN transit_vehicles tv ON sl.vehicle_ref = tv.id
            WHERE sl.operator_ref = ?
            ORDER BY sl.break_start DESC
            """;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, operatorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ServiceLogDTO log = new ServiceLogDTO();
                    log.setId(rs.getInt("id"));
                    log.setVehicleRef(rs.getString("vehicle_ref"));
                    
                    Timestamp startTimestamp = rs.getTimestamp("break_start");
                    if (startTimestamp != null) {
                        log.setBreakStart(startTimestamp);
                    }
                    
                    Timestamp endTimestamp = rs.getTimestamp("break_end");
                    if (endTimestamp != null) {
                        log.setBreakEnd(endTimestamp);
                    }
                    
                    log.setLogType(rs.getString("log_type"));
                    log.setOperatorRef(rs.getInt("operator_ref"));
                    log.setOperatorName(rs.getString("operator_name"));
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
     * Inserts a new service log record into the data source.
     *
     * @param serviceLog The {@link ServiceLogDTO} object containing the service log data to be inserted.
     * @return {@code true} if the insertion was successful, {@code false} otherwise.
     */
    @Override
    public boolean insertServiceLog(ServiceLogDTO serviceLog) {
        String sql = """
            INSERT INTO service_logs (vehicle_ref, break_start, log_type, operator_ref)
            VALUES (?, ?, ?, ?)
            """;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, serviceLog.getVehicleRef());
            stmt.setTimestamp(2, serviceLog.getBreakStart());
            stmt.setString(3, serviceLog.getLogType());
            stmt.setInt(4, serviceLog.getOperatorRef());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Updates an existing service log record to mark it as ended with the current timestamp.
     *
     * @param logId The unique identifier of the service log to be ended.
     * @return {@code true} if the update was successful, {@code false} otherwise.
     */
    @Override
    public boolean endServiceLog(int logId) {
        String sql = "UPDATE service_logs SET break_end = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, logId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
