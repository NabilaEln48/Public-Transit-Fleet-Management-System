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

/** This abstraction cost analysis data access object that calls to its implementation class for performing CRUD operations.
 * @author Zhiru Xie
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.DataAccessLayer
 */
public interface CostAnalysisDAO {
    /**
     * Get all schedules
     * @return A list of CostAnalysisDTO records from the database
     */
    List<CostAnalysisDTO> getAll();
    
    /**
     * Get single schedule
     * @param recordId The unique identifier for cost analysis report
     * @return A single CostAnalysisDTO record from the database
     */
    CostAnalysisDTO getSingleById(int recordId);
    
    /**
     * Insert a new record into the database
     * User the constructor without id
     * @param record A single CostAnalysisDTO record to insert into the database
     * @return A Boolean result. true for success and false for failure
     */
    boolean add(CostAnalysisDTO record);
    
    /**
     * Update schedule
     * Use the constructor with id
     * @param updatedRecord A CostAnalysisDTO record to be updated into the database
     * @return A Boolean result. true for success and false for failure
     */
    boolean update(CostAnalysisDTO updatedRecord);
    
    /**
     * Delete schedule
     * @param recordId An id to locate the record to delete from the database
     * @return A Boolean result. true for success and false for failure
     */
    boolean delete(int recordId);
}
