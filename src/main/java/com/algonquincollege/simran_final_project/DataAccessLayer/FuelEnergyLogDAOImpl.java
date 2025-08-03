package com.algonquincollege.simran_final_project.dataaccesslayer;

import com.algonquincollege.simran_final_project.transferobject.FuelEnergyLogDTO;
import com.algonquincollege.simran_final_project.DataAccessLayer.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation for FuelEnergyLogDAO.
 */
public class FuelEnergyLogDAOImpl implements FuelEnergyLogDAO {

    @Override
    public List<FuelEnergyLogDTO> getLogsByVehicle(String vehicleRef) throws SQLException {
        List<FuelEnergyLogDTO> logs = new ArrayList<>();
        String sql = "SELECT * FROM fuel_energy_logs WHERE vehicle_ref = ? ORDER BY recorded_at DESC";

        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vehicleRef);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FuelEnergyLogDTO dto = new FuelEnergyLogDTO(
                            rs.getInt("id"),
                            rs.getString("vehicle_ref"),
                            rs.getString("energy_type"),
                            rs.getDouble("quantity_used"),
                            rs.getDouble("km_covered"),
                            rs.getTimestamp("recorded_at").toLocalDateTime()
                    );
                    logs.add(dto);
                }
            }
        }
        return logs;
    }

    @Override
    public void insertLog(FuelEnergyLogDTO log) throws SQLException {
        String sql = "INSERT INTO fuel_energy_logs (vehicle_ref, energy_type, quantity_used, km_covered, recorded_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, log.getVehicleRef());
            ps.setString(2, log.getEnergyType());
            ps.setDouble(3, log.getQuantityUsed());
            ps.setDouble(4, log.getKmCovered());
            ps.setTimestamp(5, Timestamp.valueOf(log.getRecordedAt()));
            ps.executeUpdate();
        }
    }
}
