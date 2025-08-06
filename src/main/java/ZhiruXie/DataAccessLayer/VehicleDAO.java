
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
import java.util.List;
import java.sql.SQLException;

public interface VehicleDAO {
    List<VehicleDTO> getAll() throws SQLException;
    VehicleDTO getVehicleById(String vehicleId) throws SQLException;
    boolean add(VehicleDTO vehicle) throws SQLException;
    boolean update(VehicleDTO vehicle) throws SQLException;
    boolean delete(String vehicleId) throws SQLException;
}
