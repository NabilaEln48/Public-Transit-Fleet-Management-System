/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Simran.dataaccesslayer;

/**
 *
 * @author simra
 */
import Simran.transferobject.FuelEnergyLogDTO;
import com.group.project.nabila.msiah.DataAccessLayer.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FuelEnergyLogDAOImpl implements FuelEnergyLogDAO {
    private final Connection conn = DataSource.getInstance().getConnection();

    @Override
    public List<FuelEnergyLogDTO> getAll() {
        List<FuelEnergyLogDTO> logs = new ArrayList<>();
        String sql = "SELECT * FROM fuel_energy_logs";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                logs.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    @Override
    public FuelEnergyLogDTO getById(int id) {
        String sql = "SELECT * FROM fuel_energy_logs WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean add(FuelEnergyLogDTO log) {
        String sql = "INSERT INTO fuel_energy_logs (vehicle_ref, energy_type, quantity_used, km_covered, recorded_at) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, log.getVehicleRef());
            ps.setString(2, log.getEnergyType());
            ps.setDouble(3, log.getQuantityUsed());
            ps.setDouble(4, log.getKmCovered());
            ps.setTimestamp(5, Timestamp.valueOf(log.getRecordedAt()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(FuelEnergyLogDTO log) {
        String sql = "UPDATE fuel_energy_logs SET vehicle_ref=?, energy_type=?, quantity_used=?, km_covered=?, recorded_at=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, log.getVehicleRef());
            ps.setString(2, log.getEnergyType());
            ps.setDouble(3, log.getQuantityUsed());
            ps.setDouble(4, log.getKmCovered());
            ps.setTimestamp(5, Timestamp.valueOf(log.getRecordedAt()));
            ps.setInt(6, log.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM fuel_energy_logs WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private FuelEnergyLogDTO mapResultSet(ResultSet rs) throws SQLException {
        return new FuelEnergyLogDTO(
            rs.getInt("id"),
            rs.getString("vehicle_ref"),
            rs.getString("energy_type"),
            rs.getDouble("quantity_used"),
            rs.getDouble("km_covered"),
            rs.getTimestamp("recorded_at").toLocalDateTime()
        );
    }
}