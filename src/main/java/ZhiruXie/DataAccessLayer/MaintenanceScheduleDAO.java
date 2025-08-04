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

/**
 *
 * @author 61963
 */
public interface MaintenanceScheduleDAO {
    /**
     * Get all schedules
     * @param userId
     * @return
     */
    List<MaintenanceScheduleDTO> getAll(int userId);
    
    /**
     * Get single schedule
     * @param userId
     * @param scheduleId
     * @return
     */
    MaintenanceScheduleDTO getSingleById(int userId, int scheduleId);
    
    /**
     * Insert
     * User the constructor without id
     * @param schedule
     * @return
     */
    boolean add(MaintenanceScheduleDTO schedule);
    
    /**
     * Update
     * Use the constructor with id
     * @param schedule
     * @return
     */
    boolean update(MaintenanceScheduleDTO schedule);
    
    /**
     * Delete
     * @param scheduleId
     * @return
     */
    boolean delete(int scheduleId);
}
