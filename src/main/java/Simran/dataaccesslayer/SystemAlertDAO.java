/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Simran.dataaccesslayer;

/**
 *
 * @author simra
 */


import Simran.transferobject.SystemAlertDTO;
import java.util.List;

public interface SystemAlertDAO {
    boolean addAlert(SystemAlertDTO alert);
    List<SystemAlertDTO> getAllAlerts();
    boolean updateAlertStatus(int id, String newStatus);
}