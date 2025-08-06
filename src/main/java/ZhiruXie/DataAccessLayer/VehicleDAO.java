/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZhiruXie.DataAccessLayer;

import ZhiruXie.DTO.VehicleDTO;
import java.util.List;


public interface VehicleDAO {
    /**
     * Get all vehicles
     * @return
     */
    List<VehicleDTO> getAll();
    
    /**
     * Get single vehicle
     * @param vehicleId
     * @return
     */
    VehicleDTO getSingleById(String vehicleId);
    
    /**
     * Insert
     * User the constructor without id
     * @param vehicle
     * @return
     */
    boolean add(VehicleDTO vehicle);
    
    /**
     * Update
     * Use the constructor with id
     * @param vehicle
     * @return
     */
    boolean update(VehicleDTO vehicle);
    
    /**
     * Delete
     * @param vehicle
     * @return
     */
    boolean delete(String vehicle);
}
