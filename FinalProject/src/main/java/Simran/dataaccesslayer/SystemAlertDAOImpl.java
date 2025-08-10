/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Prabhsimran Kaur
 * Student Id: 041119310
 * Professor Name: Teddy Yap
 */

package Simran.dataaccesslayer;

import Simran.transferobject.SystemAlertDTO;
import com.group.project.nabila.msiah.DataAccessLayer.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the SystemAlertDAO interface.
 * 
 * Provides database access methods for adding, retrieving,
 * and updating system alerts.
 * 
 * Uses JDBC with prepared statements to ensure safe and efficient
 * database interaction. A shared connection is obtained from  DataSource.
 * 
 * @author 
 * Prabhsimran Kaur
 */
public class SystemAlertDAOImpl implements SystemAlertDAO {

    /** Shared database connection from DataSource singleton. */
    private final Connection conn = DataSource.getInstance().getConnection();

    /**
     * Inserts a new system alert into the database.
     *
     * @param alert the alert object containing details to store
     * @return true if insertion was successful, false otherwise
     */
    @Override
    public boolean addAlert(SystemAlertDTO alert) {
        String sql = "INSERT INTO system_alerts (alert_category, vehicle_ref, alert_message, alert_time, resolution_state) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, alert.getAlertCategory());
            ps.setString(2, alert.getVehicleRef());
            ps.setString(3, alert.getAlertMessage());
            ps.setTimestamp(4, Timestamp.valueOf(alert.getAlertTime()));
            ps.setString(5, alert.getResolutionState());

            return ps.executeUpdate() > 0; // returns true if at least one row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all system alerts from the database,
     * ordered by alert time in descending order.
     *
     * @return list of all system alerts
     */
    @Override
    public List<SystemAlertDTO> getAllAlerts() {
        List<SystemAlertDTO> alerts = new ArrayList<>();
        String sql = "SELECT * FROM system_alerts ORDER BY alert_time DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Loop through the results and map each row to a SystemAlertDTO
            while (rs.next()) {
                SystemAlertDTO alert = new SystemAlertDTO();
                alert.setId(rs.getInt("id"));
                alert.setAlertCategory(rs.getString("alert_category"));
                alert.setVehicleRef(rs.getString("vehicle_ref"));
                alert.setAlertMessage(rs.getString("alert_message"));
                alert.setAlertTime(rs.getTimestamp("alert_time").toLocalDateTime());
                alert.setResolutionState(rs.getString("resolution_state"));
                alerts.add(alert);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alerts;
    }

    /**
     * Updates the resolution status of a system alert by ID.
     *
     * @param id the ID of the alert to update
     * @param newStatus the new resolution state
     * @return true if the update was successful, false otherwise
     */
    @Override
    public boolean updateAlertStatus(int id, String newStatus) {
        String sql = "UPDATE system_alerts SET resolution_state=? WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0; // returns true if at least one row was updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}