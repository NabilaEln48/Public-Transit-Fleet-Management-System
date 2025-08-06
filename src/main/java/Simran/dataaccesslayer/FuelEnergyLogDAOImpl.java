/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Prabhsimran Kaur
 * Student Id: 041119310
 * Professor Name: Teddy Yap
 */

package Simran.dataaccesslayer;

import Simran.transferobject.FuelEnergyLogDTO;
import com.group.project.nabila.msiah.DataAccessLayer.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the  FuelEnergyLogDAO interface.
 * 
 * Provides database access methods for performing CRUD operations
 * on the fuel_energy_logs table.
 * 
 * Uses JDBC with prepared statements to interact with the database.
 * A shared connection is obtained from Data Source.
 * 
 * @author Prabhsimran Kaur
 */
public class FuelEnergyLogDAOImpl implements FuelEnergyLogDAO {

    /** Shared database connection from DataSource singleton. */
    private final Connection conn = DataSource.getInstance().getConnection();

    /**
     * Retrieves all fuel/energy logs from the database.
     *
     * @return list of all logs, empty list if none found
     */
    @Override
    public List<FuelEnergyLogDTO> getAll() {
        List<FuelEnergyLogDTO> logs = new ArrayList<>();
        String sql = "SELECT * FROM fuel_energy_logs";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Map each result set row to a DTO and add to list
            while (rs.next()) {
                logs.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    /**
     * Retrieves a fuel/energy log by its ID.
     *
     * @param id the log ID
     * @return the matching log, or null if not found
     */
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

    /**
     * Adds a new fuel/energy log to the database.
     *
     * @param log the log to add
     * @return true if inserted successfully, false otherwise
     */
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

    /**
     * Updates an existing fuel/energy log in the database.
     *
     * @param log the updated log data
     * @return true if updated successfully, false otherwise
     */
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

    /**
     * Deletes a fuel/energy log from the database by ID.
     *
     * @param id the log ID
     * @return true if deleted successfully, false otherwise
     */
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

    /**
     * Maps a database result set row to a FuelEnergyLogDTO.
     *
     * @param rs the result set
     * @return a populated FuelEnergyLogDTO
     * @throws SQLException if a database access error occurs
     */
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