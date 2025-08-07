/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
 */
package ZhiruXie.DataAccessLayer;

import ZhiruXie.DTO.MaintenanceScheduleDTO;
import java.util.List;

/** This maintenance schedule data access object that calls to its implementation class for performing CRUD operations.
 * @author Zhiru Xie
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.DataAccessLayer
 */
public interface MaintenanceScheduleDAO {
    /**
     * Get all schedule records
     * @param userId The unique identifier for the user
     * @return A list of MaintenanceScheduleDTO records from the database
     */
    List<MaintenanceScheduleDTO> getAll(int userId);
    
    /**
     * Get single schedule
     * @param userId The unique identifier for the user
     * @param scheduleId The unique identifier for the maintenance schedule report
     * @return A single MaintenanceScheduleDTO record from the database
     */
    MaintenanceScheduleDTO getSingleById(int userId, int scheduleId);
    
    /**
     * Insert a new maintenance schedule record
     * @param schedule A new schedule DTO
     * @return A Boolean result. true for success and false for failure
     */
    boolean add(MaintenanceScheduleDTO schedule);
    
    /**
     * Update an existing maintenance schedule record
     * @param schedule Updated schedule DTO
     * @return A Boolean result. true for success and false for failure
     */
    boolean update(MaintenanceScheduleDTO schedule);
    
    /**
     * Delete an existing maintenance schedule record
     * @param scheduleId Target record id
     * @return A Boolean result. true for success and false for failure
     */
    boolean delete(int scheduleId);
}
