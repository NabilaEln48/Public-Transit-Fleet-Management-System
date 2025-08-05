/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Simran.dataaccesslayer;

/**
 *
 * @author simra
 */
import Simran.transferobject.SystemAlertDTO;
import com.group.project.nabila.msiah.DataAccessLayer.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SystemAlertDAOImpl implements SystemAlertDAO {

    private final Connection conn = DataSource.getInstance().getConnection();

    @Override
    public boolean addAlert(SystemAlertDTO alert) {
        String sql = "INSERT INTO system_alerts (alert_category, vehicle_ref, alert_message, alert_time, resolution_state) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, alert.getAlertCategory());
            ps.setString(2, alert.getVehicleRef());
            ps.setString(3, alert.getAlertMessage());
            ps.setTimestamp(4, Timestamp.valueOf(alert.getAlertTime()));
            ps.setString(5, alert.getResolutionState());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<SystemAlertDTO> getAllAlerts() {
        List<SystemAlertDTO> alerts = new ArrayList<>();
        String sql = "SELECT * FROM system_alerts ORDER BY alert_time DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
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

    @Override
    public boolean updateAlertStatus(int id, String newStatus) {
        String sql = "UPDATE system_alerts SET resolution_state=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}