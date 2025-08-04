/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
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
