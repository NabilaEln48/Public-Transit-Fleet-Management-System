/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
 */
package ZhiruXie.BusinessLayer;

import ZhiruXie.DTO.VehicleDTO;
import ZhiruXie.DataAccessLayer.VehicleDAO;
import ZhiruXie.DataAccessLayer.VehicleDAOImp;
import java.util.List;

/** This class contains the encapsulated CRUD operations for vehicle management.Including get single and get all.
 * @author Zhiru Xie
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.BusinessLayer
 */
public class VehicleBusinessLogic implements BusinessLogic<VehicleDTO> {
    /** A DAO variable used for storing its respective DAO Implementation class instance. */
    private VehicleDAO dao = null;
    
    /**
     * Default constructor with no parameter.Initializes the CostAnalysisDAO implementation class instance.
    */
    public VehicleBusinessLogic() {dao = new VehicleDAOImp();}
    
    /** 
     * Get all records from the database.
     * @param params Optional parameters that sometimes contain an id
     * @return A list of translated DTO records from the database
    */
    @Override
    public List<VehicleDTO> getAll(int... params) {
        return dao.getAll();
    }

    /** 
     * Insert a single record into the database.
     * @param params Mandatory parameters that at least contain a DTO object
     * @return A Boolean result. true for success and false for failure
    */
    @Override
    public VehicleDTO getSingleById(Object... params) {
        return dao.getSingleById(params[0].toString());
    }
    
    /** 
     * Update information of a single record from the database based on the new attribute values.
     * @param params Mandatory parameters that at least contain a DTO object
     * @return A Boolean result. true for success and false for failure
    */
    @Override
    public boolean add(Object... params) {
        return dao.add((VehicleDTO)params[0]);
    }
    
    /** 
     * Update information of a single record from the database based on the new attribute values.
     * @param params Mandatory parameters that at least contain a DTO object
     * @return A Boolean result. true for success and false for failure
    */
    @Override
    public boolean update(Object... params) {
        return dao.update((VehicleDTO)params[0]);
    }
    
    /** 
     * Delete a single record from the database based on the id.
     * @param params Mandatory parameters that at least contain an id
     * @return A Boolean result. true for success and false for failure
    */
    @Override
    public boolean delete(Object... params) {
        return dao.delete(params[0].toString());
    }
}
