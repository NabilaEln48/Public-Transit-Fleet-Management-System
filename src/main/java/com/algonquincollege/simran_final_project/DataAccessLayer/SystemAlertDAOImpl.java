/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.algonquincollege.simran_final_project.dataaccesslayer;

import com.algonquincollege.simran_final_project.transferobject.SystemAlertDTO;
import com.algonquincollege.simran_final_project.DataAccessLayer.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation for SystemAlertDAO.
 */
public class SystemAlertDAOImpl implements SystemAlertDAO {

    @Override
    public void insertAlert(SystemAlertDTO alert) throws SQLException {
        String sql = "INSERT INTO system_alerts (alert_category, vehicle_ref, alert_message, alert_time, resolution_state) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, alert.getAlertCategory());
            ps.setString(2, alert.getVehicleRef());
            ps.setString(3, alert.getAlertMessage());
            ps.setTimestamp(4, Timestamp.valueOf(alert.getAlertTime()));
            ps.setString(5, alert.getResolutionState());
            ps.executeUpdate();
        }
    }

    @Override
    public List<SystemAlertDTO> getAlertsByVehicle(String vehicleRef) throws SQLException {
        List<SystemAlertDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM system_alerts WHERE vehicle_ref = ?";
        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vehicleRef);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SystemAlertDTO dto = new SystemAlertDTO(
                            rs.getInt("id"),
                            rs.getString("alert_category"),
                            rs.getString("vehicle_ref"),
                            rs.getString("alert_message"),
                            rs.getTimestamp("alert_time").toLocalDateTime(),
                            rs.getString("resolution_state")
                    );
                    list.add(dto);
                }
            }
        }
        return list;
    }
}
