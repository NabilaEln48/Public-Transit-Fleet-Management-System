/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
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
    List<MaintenanceScheduleDTO> getAllMaintenaceSchedules(int userId);
    
    /**
     * Get single schedule
     * @param userId
     * @param scheduleId
     * @return
     */
    MaintenanceScheduleDTO getMaintenaceScheduleById(int userId, int scheduleId);
    
    /**
     * Insert
     * User the constructor without id
     * @param userId
     * @param schedule
     * @return
     */
    boolean addMaintenaceSchedule(int userId, MaintenanceScheduleDTO schedule);
    
    /**
     * Update
     * Use the constructor with id
     * @param userId
     * @param schedule
     * @return
     */
    boolean updateMaintenanceSchedule(int userId, MaintenanceScheduleDTO schedule);
    
    /**
     * Delete
     * @param userId
     * @param scheduleId
     * @return
     */
    boolean deleteMaintenanceSchedule(int userId, int scheduleId);
}
