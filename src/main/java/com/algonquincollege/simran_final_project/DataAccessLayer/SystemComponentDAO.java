/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.algonquincollege.simran_final_project.dataaccesslayer;

import com.algonquincollege.simran_final_project.transferobject.SystemComponentDTO;
import java.util.List;

/**
 * DAO interface for system_components table.
 */
public interface SystemComponentDAO {
    List<SystemComponentDTO> getComponentsByVehicle(String vehicleRef) throws Exception;
    void updateComponentStatus(SystemComponentDTO component) throws Exception;
}
