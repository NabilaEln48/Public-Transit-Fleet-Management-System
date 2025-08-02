/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZhiruXie.DataAccessLayer;

import ZhiruXie.DTO.PerformanceDTO;
import java.util.List;

/**
 *
 * @author 61963
 */
public interface PerformanceDAO {
    /**
     * Get all schedules
     * @param userId
     * @return
     */
    List<PerformanceDTO> getAll(int userId);
    
    /**
     * Get single schedule
     * @param userId
     * @param scheduleId
     * @return
     */
    PerformanceDTO getSingleById(int userId, int scheduleId);
    
    /**
     * Insert
     * User the constructor without id
     * @param userId
     * @param schedule
     * @return
     */
    boolean add(int userId, PerformanceDTO performance);
    
    /**
     * Update
     * Use the constructor with id
     * @param userId
     * @param schedule
     * @return
     */
    boolean update(int userId, PerformanceDTO performance);
    
    /**
     * Delete
     * @param userId
     * @param scheduleId
     * @return
     */
    boolean delete(int userId, int scheduleId);
}
