/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
 */
package ZhiruXie.DataAccessLayer;

import ZhiruXie.DTO.VehicleDTO;
import ZhiruXie.enums.Enum_VehicleCategory;
import ZhiruXie.enums.Enum_VehicleOperationalState;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** This implementation class for vehicle data access object that contains detailed logic for CRUD operations.
 * @author Zhiru Xie
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.DataAccessLayer
 */
public class VehicleDAOImp implements VehicleDAO {

    /**
     * Get all vehicles
     * @return A list of vehicle records from the database
     */
    @Override
    public List<VehicleDTO> getAll() {
        String sql = "SELECT * FROM ptfms_db.transit_vehicles";
        List<VehicleDTO> vehicles = new ArrayList<>();
        try (Connection con = DataSource.getConnection("cst8288", "cst8288");
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                VehicleDTO vehicle = new VehicleDTO(
                        rs.getString("id"),
                        Enum_VehicleCategory.valueOf(rs.getString("category")),
                        rs.getString("registration_number"),
                        rs.getString("fuel_used"),
                        rs.getDouble("efficiency_rate"),
                        rs.getInt("capacity"),
                        rs.getString("assigned_route"),
                        Enum_VehicleOperationalState.valueOf(rs.getString("operational_state"))
                );
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching vehicles: " + e.getMessage());
        }
        return vehicles;
    }

    /**
     * Get a single vehicle
     * @param vehicleId Unique identifier for the vehicle
     * @return A single vehicle record from the database
     */
    @Override
    public VehicleDTO getSingleById(String vehicleId) {
        String sql = "SELECT * FROM ptfms_db.transit_vehicles WHERE id = ?";
        VehicleDTO vehicle = null;

        try (Connection con = DataSource.getConnection("cst8288", "cst8288");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, vehicleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    vehicle = new VehicleDTO(
                            rs.getString("id"),
                            Enum_VehicleCategory.valueOf(rs.getString("category")),
                            rs.getString("registration_number"),
                            rs.getString("fuel_used"),
                            rs.getDouble("efficiency_rate"),
                            rs.getInt("capacity"),
                            rs.getString("assigned_route"),
                            Enum_VehicleOperationalState.valueOf(rs.getString("operational_state"))
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching vehicle by ID: " + e.getMessage());
        }
        return vehicle;
    }

    /**
     * Insert a new vehicle into the database.
     * User the constructor without id
     * @param vehicle A new vehicle instance
     * @return A Boolean result. true for success and false for failure
     */
    @Override
    public boolean add(VehicleDTO vehicle) {
        String sql = "INSERT INTO ptfms_db.transit_vehicles " +
                "(id, category, registration_number, fuel_used, efficiency_rate, capacity, assigned_route, operational_state) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DataSource.getConnection("cst8288", "cst8288");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, vehicle.getId());
            pstmt.setString(2, vehicle.getCategory().name());
            pstmt.setString(3, vehicle.getRegistrationNumber());
            pstmt.setString(4, vehicle.getFuelUsed());
            pstmt.setDouble(5, vehicle.getEfficiencyRate());
            pstmt.setInt(6, vehicle.getCapacity());
            pstmt.setString(7, vehicle.getAssigned_route());
            pstmt.setString(8, vehicle.getOperationalState().name());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding vehicle: " + e.getMessage());
            return false;
        }
    }

    /**
     * Update an existing vehicle from the database.
     * Use the constructor with id
     * @param vehicle An updated vehicle instance
     * @return A Boolean result. true for success and false for failure
     */
    @Override
    public boolean update(VehicleDTO vehicle) {
        String sql = "UPDATE ptfms_db.transit_vehicles SET " +
                "category = ?, registration_number = ?, fuel_used = ?, efficiency_rate = ?, capacity = ?, assigned_route = ?, operational_state = ? " +
                "WHERE id = ?";
        try (Connection con = DataSource.getConnection("cst8288", "cst8288");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, vehicle.getCategory().name());
            pstmt.setString(2, vehicle.getRegistrationNumber());
            pstmt.setString(3, vehicle.getFuelUsed());
            pstmt.setDouble(4, vehicle.getEfficiencyRate());
            pstmt.setInt(5, vehicle.getCapacity());
            pstmt.setString(6, vehicle.getAssigned_route());
            pstmt.setString(7, vehicle.getOperationalState().name());
            pstmt.setString(8, vehicle.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating vehicle: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete an existing vehicle from the database.
     * @param vehicleId Target record id
     * @return A Boolean result. true for success and false for failure
     */
    @Override
    public boolean delete(String vehicleId) {
        // If you have foreign keys with ON DELETE CASCADE, this is enough.
        String sql = "DELETE FROM ptfms_db.transit_vehicles WHERE id = ?";
        try (Connection con = DataSource.getConnection("cst8288", "cst8288");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, vehicleId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting vehicle: " + e.getMessage());
            return false;
        }
    }
}
