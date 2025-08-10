/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
 */
package ZhiruXie.DataAccessLayer;

import ZhiruXie.DTO.PerformanceDTO;
import java.util.List;

/** This performance report data access object that calls to its implementation class for performing CRUD operations.
 * @author Zhiru Xie
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.DataAccessLayer
 */
public interface PerformanceDAO {
    /**
     * Get all performance report
     * @param userId Unique Identifier of the user
     * @return A list of performance report records from the database
     */
    List<PerformanceDTO> getAll(int userId);
    
    /**
     * Get a single performance report
     * @param userId Unique Identifier of the user
     * @param performanceId Unique Identifier of the performance report
     * @return A single performance record from the database
     */
    PerformanceDTO getSingleById(int userId, int performanceId);
    
    /**
     * Insert a new performance report into the database
     * @param userId Unique identifier for the user
     * @param performance A new performance report instance
     * @return A Boolean result. true for success and false for failure
     */
    boolean add(int userId, PerformanceDTO performance);
    
    /**
     * Update an existing performance report from the database
     * @param userId Unique identifier for the user
     * @param performance An updated performance report instance
     * @return A Boolean result. true for success and false for failure
     */
    boolean update(int userId, PerformanceDTO performance);
    
    /**
     * Delete an existing performance report from the database
     * @param userId Unique identifier for the user
     * @param recordId Unique identifier for the performance report
     * @return A Boolean result. true for success and false for failure
     */
    boolean delete(int userId, int recordId);
}
