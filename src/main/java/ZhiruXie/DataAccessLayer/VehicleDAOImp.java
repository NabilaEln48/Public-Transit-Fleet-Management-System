
/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Purnima Purnima
 * Student Id: 041161250
 * Professor Name: Teddy Yap
 */
package ZhiruXie.DataAccessLayer;

import ZhiruXie.DTO.VehicleDTO;
import ZhiruXie.enums.Enum_VehicleCategory;
import ZhiruXie.enums.Enum_VehicleOperationalState;
// import ZhiruXie.DataAccessLayer.DataSource; // Not strictly needed if connection is injected

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAOImp implements VehicleDAO {

    // Declare the connection field
    private Connection connection; 

    // FIX: Assign the connection in the constructor
    public VehicleDAOImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<VehicleDTO> getAll() throws SQLException { // Declare SQLException to propagate errors
        // Removed database name from SQL as it's typically set at connection level
        String sql = "SELECT id, vehicle_number, category, registration_number, " +
                     "fuel_used, efficiency_rate, capacity, assigned_route, operational_state " +
                     "FROM transit_vehicles ORDER BY vehicle_number ASC"; // Added vehicle_number to select

        List<VehicleDTO> vehicles = new ArrayList<>();
        
        // Use the injected connection, not a new one
        try (PreparedStatement pstmt = connection.prepareStatement(sql); 
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
                // If you add vehicleNumber to your DTO, you'd need to set it here too:
                // vehicle.setVehicleNumber(rs.getString("vehicle_number"));
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching vehicles: " + e.getMessage()); // Use err for errors
            e.printStackTrace();
            throw e; // Re-throw the exception
        } catch (IllegalArgumentException e) {
            System.err.println("Error converting database string to enum for category or operational state: " + e.getMessage());
            e.printStackTrace();
            throw new SQLException("Invalid enum value found in database for transit vehicles.", e);
        }
        return vehicles;
    }

    @Override
    public VehicleDTO getVehicleById(String vehicleId) throws SQLException { // Declare SQLException
        String sql = "SELECT id, vehicle_number, category, registration_number, " +
                     "fuel_used, efficiency_rate, capacity, assigned_route, operational_state " +
                     "FROM transit_vehicles WHERE id = ?"; // Added vehicle_number to select
        VehicleDTO vehicle = null;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) { // Use injected connection
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
                    // If you add vehicleNumber to your DTO, you'd need to set it here too:
                    // vehicle.setVehicleNumber(rs.getString("vehicle_number"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching vehicle by ID: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw the exception
        } catch (IllegalArgumentException e) {
            System.err.println("Error converting database string to enum for category or operational state: " + e.getMessage());
            e.printStackTrace();
            throw new SQLException("Invalid enum value found in database for transit vehicles by ID.", e);
        }
        return vehicle;
    }

    @Override
    public boolean add(VehicleDTO vehicle) throws SQLException { // Declare SQLException
        String sql = "INSERT INTO transit_vehicles " + // Removed database name
                "(id, vehicle_number, category, registration_number, fuel_used, efficiency_rate, capacity, assigned_route, operational_state) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"; // Added vehicle_number placeholder
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) { // Use injected connection
            pstmt.setString(1, vehicle.getId());
            // If you add vehicleNumber to your DTO, you'd need to get it from there:
            // pstmt.setString(2, vehicle.getVehicleNumber());
            pstmt.setString(2, vehicle.getCategory().name()); // Adjusted index if vehicleNumber is added
            pstmt.setString(3, vehicle.getRegistrationNumber()); // Adjusted index
            pstmt.setString(4, vehicle.getFuelUsed()); // Adjusted index
            pstmt.setDouble(5, vehicle.getEfficiencyRate()); // Adjusted index
            pstmt.setInt(6, vehicle.getCapacity()); // Adjusted index
            pstmt.setString(7, vehicle.getAssigned_route()); // Adjusted index
            pstmt.setString(8, vehicle.getOperationalState().name()); // Adjusted index
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding vehicle: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw the exception
        }
    }

    @Override
    public boolean update(VehicleDTO vehicle) throws SQLException { // Declare SQLException
        String sql = "UPDATE transit_vehicles SET " + // Removed database name
                "category = ?, registration_number = ?, fuel_used = ?, efficiency_rate = ?, capacity = ?, assigned_route = ?, operational_state = ? " +
                "WHERE id = ?";
        try (Connection con = connection; // Use injected connection
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
            System.err.println("Error updating vehicle: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw the exception
        }
    }

    @Override
    public boolean delete(String vehicleId) throws SQLException { // Declare SQLException
        String sql = "DELETE FROM transit_vehicles WHERE id = ?"; // Removed database name
        try (Connection con = connection; // Use injected connection
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, vehicleId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting vehicle: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw the exception
        }
    }
}