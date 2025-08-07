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
import java.util.List;

/** This vehicle data access object that calls to its implementation class for performing CRUD operations.
 * @author Zhiru Xie
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.DataAccessLayer
 */
public interface VehicleDAO {
    /**
     * Get all vehicles
     * @return A list of vehicle records from the database
     */
    List<VehicleDTO> getAll();
    
    /**
     * Get a single vehicle
     * @param vehicleId Unique identifier for the vehicle
     * @return A single vehicle record from the database
     */
    VehicleDTO getSingleById(String vehicleId);
    
    /**
     * Insert a new vehicle into the database.
     * User the constructor without id
     * @param vehicle A new vehicle instance
     * @return A Boolean result. true for success and false for failure
     */
    boolean add(VehicleDTO vehicle);
    
    /**
     * Update an existing vehicle from the database.
     * Use the constructor with id
     * @param vehicle An updated vehicle instance
     * @return A Boolean result. true for success and false for failure
     */
    boolean update(VehicleDTO vehicle);
    
    /**
     * Delete an existing vehicle from the database.
     * @param vehicleId Target record id
     * @return A Boolean result. true for success and false for failure
     */
    boolean delete(String vehicleId);
}
