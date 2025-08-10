/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Prabhsimran Kaur
 * Student Id: 041119310
 * Professor Name: Teddy Yap
 */

package Simran.alerts;

import Simran.dataaccesslayer.SystemAlertDAO;
import Simran.dataaccesslayer.SystemAlertDAOImpl;
import Simran.transferobject.SystemAlertDTO;

import java.time.LocalDateTime;

/**
 * Concrete Observer implementation that stores alerts in the database.
 * 
 * This class is part of the Observer design pattern. It listens for alerts 
 * triggered by the Subject and 
 * persists them into the system alerts table in the database.
 * 
 * @author Prabhsimran Kaur
 */
public class DatabaseAlertObserver implements Observer {

    /** Data Access Object for persisting system alerts into the database. */
    private final SystemAlertDAO alertDAO = new SystemAlertDAOImpl();

    /**
     * Handles incoming alert notifications from the Subject.
     * 
     *
     * @param alertCategory the category of the alert (e.g., "Fuel", "Energy")
     * @param vehicleRef    the reference ID of the affected vehicle
     * @param alertMessage  the message describing the alert
     * @param severity      the severity of the alert (e.g., "CRITICAL", "WARNING")
     */
    @Override
    public void update(String alertCategory, String vehicleRef, String alertMessage, String severity) {
        // Create a new alert DTO to hold alert details
        SystemAlertDTO alert = new SystemAlertDTO();

        // Assign alert category (type of alert such as Fuel/Energy)
        alert.setAlertCategory(alertCategory);

        // Assign the vehicle reference to link the alert to a specific vehicle
        alert.setVehicleRef(vehicleRef);

        // Prepend severity to the alert message for emphasis
        alert.setAlertMessage("[" + severity + "] " + alertMessage);

        // Set the current time as the alert trigger time
        alert.setAlertTime(LocalDateTime.now());

        // Initially mark the alert as "Pending" until it's resolved
        alert.setResolutionState("Pending");

        // Save the alert into the database via DAO
        alertDAO.addAlert(alert);
    }
}