/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZhiruXie.DataAccessLayer;

import ZhiruXie.DTO.CostAnalysisDTO;
import java.util.List;

/**
 *
 * @author 61963
 */
public interface CostAnalysisDAO {
        /**
     * Get all schedules
     * @return
     */
    List<CostAnalysisDTO> getAll();
    
    /**
     * Get single schedule
     * @param recordId
     * @return
     */
    CostAnalysisDTO getSingleById(int recordId);
    
    /**
     * Insert
     * User the constructor without id
     * @param record
     * @return
     */
    boolean add(CostAnalysisDTO record);
    
    /**
     * Update
     * Use the constructor with id
     * @param updatedRecord
     * @return
     */
    boolean update(CostAnalysisDTO updatedRecord);
    
    /**
     * Delete
     * @param recordId
     * @return
     */
    boolean delete(int recordId);
}
