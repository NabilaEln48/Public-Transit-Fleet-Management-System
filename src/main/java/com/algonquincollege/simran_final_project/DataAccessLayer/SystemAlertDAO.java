/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.algonquincollege.simran_final_project.dataaccesslayer;

import com.algonquincollege.simran_final_project.transferobject.SystemAlertDTO;
import java.util.List;

/**
 * DAO interface for system_alerts table.
 */
public interface SystemAlertDAO {
    void insertAlert(SystemAlertDTO alert) throws Exception;
    List<SystemAlertDTO> getAlertsByVehicle(String vehicleRef) throws Exception;
}
