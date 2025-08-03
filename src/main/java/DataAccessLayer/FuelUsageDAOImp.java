/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessLayer;

import transferObject.FuelUsageDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author simra
 */
public class FuelUsageDAOImp implements FuelUsageDAO {

    @Override
    public List<FuelUsageDTO> getAllEnergyUsage() throws SQLException {
        String sql = "SELECT * FROM fuel_energy_logs";
        ArrayList<FuelUsageDTO> usageList = null;

        try (
            Connection con = DataSource.getConnection("cst8288", "cst8288");
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            usageList = new ArrayList<>();
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    FuelUsageDTO usage = new FuelUsageDTO();
                    usage.setUsageId(rs.getInt("id"));
                    usage.setVehicleId(rs.getString("vehicle_ref"));
                    usage.setEnergyType(rs.getString("energy_type"));
                    usage.setQuantityUsed(rs.getDouble("quantity_used"));
                    usage.setDistanceTraveled(rs.getDouble("km_covered"));
                    usage.setTimestamp(rs.getString("recorded_at"));
                    usageList.add(usage);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return usageList;
    }

    @Override
    public FuelUsageDTO getEnergyUsageById(int id) throws SQLException {
        // Placeholder
        return null;
    }

    @Override
    public List<FuelUsageDTO> getFilteredEnergyUsage(String energyType, String startDate, String endDate) throws SQLException {
        // Placeholder
        return null;
    }

    @Override
    public boolean addEnergyUsage(FuelUsageDTO usage) throws SQLException {
        // Placeholder
        return true;
    }

    @Override
    public boolean updateEnergyUsage(FuelUsageDTO usage) throws SQLException {
        // Placeholder
        return true;
    }

    @Override
    public boolean deleteEnergyUsage(int id) throws SQLException {
        // Placeholder
        return true;
    }
}