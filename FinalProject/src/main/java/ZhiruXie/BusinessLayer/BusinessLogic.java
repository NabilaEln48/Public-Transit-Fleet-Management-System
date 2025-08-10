/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
 */
package ZhiruXie.BusinessLayer;

import java.util.List;

/** This is the abstraction of the general business logic class that contains basic CRUD operations.
 * @author Zhiru Xie
 * @param <T> A generic type that represents applicable DTO classes
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.BusinessLayer
 */
public interface BusinessLogic<T> {
    /** 
     * Get all records from the database.
     * @param params Optional parameters that sometimes contain an id
     * @return A list of translated DTO records from the database
    */
    List<T> getAll(int... params);
    
    /** 
     * Get a single record from the database based on id.
     * @param params Optional parameters that at least contain an id
     * @return A translated DTO record from the database
    */
    T getSingleById(Object... params);
    
    /** 
     * Insert a single record into the database.
     * @param params Mandatory parameters that at least contain a DTO object
     * @return A Boolean result. true for success and false for failure
    */
    boolean add(Object... params);
    
    /** 
     * Update information of a single record from the database based on the new attribute values.
     * @param params Mandatory parameters that at least contain a DTO object
     * @return A Boolean result. true for success and false for failure
    */
    boolean update(Object... params);
    
    /** 
     * Delete a single record from the database based on the id.
     * @param params Mandatory parameters that at least contain an id
     * @return A Boolean result. true for success and false for failure
    */
    boolean delete(Object... params);
}
