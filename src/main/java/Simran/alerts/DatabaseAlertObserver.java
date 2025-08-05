/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Simran.alerts;

/**
 *
 * @author simra
 */
import Simran.dataaccesslayer.SystemAlertDAO;
import Simran.dataaccesslayer.SystemAlertDAOImpl;
import Simran.transferobject.SystemAlertDTO;

import java.time.LocalDateTime;

public class DatabaseAlertObserver implements Observer {

    private final SystemAlertDAO alertDAO = new SystemAlertDAOImpl();

    @Override
    public void update(String alertCategory, String vehicleRef, String alertMessage, String severity) {
        SystemAlertDTO alert = new SystemAlertDTO();
        alert.setAlertCategory(alertCategory);
        alert.setVehicleRef(vehicleRef);
        alert.setAlertMessage("[" + severity + "] " + alertMessage);
        alert.setAlertTime(LocalDateTime.now());
        alert.setResolutionState("Pending");
        alertDAO.addAlert(alert);
    }
}