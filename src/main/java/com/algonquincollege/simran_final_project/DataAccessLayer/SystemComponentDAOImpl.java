package com.algonquincollege.simran_final_project.dataaccesslayer;

import com.algonquincollege.simran_final_project.transferobject.SystemComponentDTO;
import com.algonquincollege.simran_final_project.DataAccessLayer.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation for SystemComponentDAO.
 */
public class SystemComponentDAOImpl implements SystemComponentDAO {

    @Override
    public List<SystemComponentDTO> getComponentsByVehicle(String vehicleRef) throws SQLException {
        List<SystemComponentDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM system_components WHERE vehicle_ref = ?";

        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vehicleRef);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SystemComponentDTO dto = new SystemComponentDTO(
                            rs.getInt("id"),
                            rs.getString("vehicle_ref"),
                            rs.getString("component_type"),
                            rs.getDouble("total_runtime"),
                            rs.getDouble("usage_wear_percent"),
                            rs.getString("status_check")
                    );
                    list.add(dto);
                }
            }
        }
        return list;
    }

    @Override
    public void updateComponentStatus(SystemComponentDTO component) throws SQLException {
        String sql = "UPDATE system_components SET usage_wear_percent = ?, status_check = ? WHERE id = ?";
        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, component.getUsageWearPercent());
            ps.setString(2, component.getStatusCheck());
            ps.setInt(3, component.getId());
            ps.executeUpdate();
        }
    }
}
