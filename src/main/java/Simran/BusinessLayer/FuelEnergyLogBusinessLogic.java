/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Prabhsimran Kaur
 * Student Id: 041119310
 * Professor Name: Teddy Yap
 */
package Simran.BusinessLayer;

/**
 * Business Logic class for managing the fuel and energy logs.
 * implements the subject interface to support the observer design pattern 
 * for sending alerts when specific fuel/energy usage conditions are met.
 * 
 * Only users with the role "Operator" can add, update, or delete the logs.
 * @author Prabhsimran Kaur
 */
import Simran.alerts.Observer;
import Simran.alerts.Subject;
import Simran.dataaccesslayer.FuelEnergyLogDAO;
import Simran.dataaccesslayer.FuelEnergyLogDAOImpl;
import Simran.transferobject.FuelEnergyLogDTO;

import java.util.ArrayList;
import java.util.List;

public class FuelEnergyLogBusinessLogic implements Subject {
    private final FuelEnergyLogDAO dao = new FuelEnergyLogDAOImpl();
    private final List<Observer> observers = new ArrayList<>();
    /**
     * Registers a new observer to receive alerts.
     *
     * @param o the observer to be registered
     */
    
    @Override
    public void registerObserver(Observer o) { observers.add(o); }

    /**
     * Removes an observer from the alert subscription list.
     *
     * @param o the observer to be removed
     */
    @Override
    public void removeObserver(Observer o) { observers.remove(o); }
    /**
     * Notifies all registered observers about an alert.
     *
     * @param alertCategory the category of the alert (e.g., "Fuel", "Energy")
     * @param vehicleRef    the reference ID of the affected vehicle
     * @param alertMessage  the descriptive message of the alert
     * @param severity      the severity level of the alert (e.g., "CRITICAL", "WARNING")
     */
    
    @Override
    public void notifyObservers(String alertCategory, String vehicleRef, String alertMessage, String severity) {
        for (Observer o : observers) {
            o.update(alertCategory, vehicleRef, alertMessage, severity);
        }
    }
    /**
     * Retrieves all fuel/energy logs from the database.
     *
     * @return a list of {@link FuelEnergyLogDTO} objects
     */
    public List<FuelEnergyLogDTO> getAll() {
        return dao.getAll();
    }
    /**
     * Retrieves a specific fuel/energy log by its ID.
     *
     * @param id the unique identifier of the log
     * @return the corresponding {@link FuelEnergyLogDTO} object, or null if not found
     */
    
    public FuelEnergyLogDTO getById(int id) {
        return dao.getById(id);
    }

    /**
     * Adds a new fuel/energy log if the user role is "OPERATOR".
     * Triggers an alert if the quantity used exceeds the threshold.
     *
     * @param log  the log data to be added
     * @param role the role of the current user
     * @return true if the log was added successfully, false otherwise
     */
    public boolean add(FuelEnergyLogDTO log, String role) {
        if (!"OPERATOR".equalsIgnoreCase(role)) return false;
        boolean success = dao.add(log);
        if (success && log.getQuantityUsed() > 1) { // Example alert trigger
            notifyObservers("Fuel", log.getVehicleRef(), "High fuel usage: " + log.getQuantityUsed(), "CRITICAL");
        }
        return success;
    }

    /**
     * Updates an existing fuel/energy log if the user role is "OPERATOR".
     * Triggers an alert if the quantity used exceeds the threshold.
     *
     * @param log  the updated log data
     * @param role the role of the current user
     * @return true if the update was successful, false otherwise
     */
    public boolean update(FuelEnergyLogDTO log, String role) {
        if (!"OPERATOR".equalsIgnoreCase(role)) return false;
        boolean success = dao.update(log);
        if (success && log.getQuantityUsed() > 10) { // Example alert trigger
            notifyObservers("Fuel", log.getVehicleRef(), "High fuel usage: " + log.getQuantityUsed(), "CRITICAL");
        }
        return success;
    }
    /**
     * Deletes a fuel/energy log if the user role is "OPERATOR".
     *
     * @param id   the ID of the log to delete
     * @param role the role of the current user
     * @return true if the log was deleted successfully, false otherwise
     */
    public boolean delete(int id, String role) {
        if (!"OPERATOR".equalsIgnoreCase(role)) return false;
        return dao.delete(id);
    }
}