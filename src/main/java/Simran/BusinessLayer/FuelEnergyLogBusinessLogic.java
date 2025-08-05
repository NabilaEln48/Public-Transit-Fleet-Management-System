/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Simran.BusinessLayer;

/**
 *
 * @author simra
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

    @Override
    public void registerObserver(Observer o) { observers.add(o); }

    @Override
    public void removeObserver(Observer o) { observers.remove(o); }

    @Override
    public void notifyObservers(String alertCategory, String vehicleRef, String alertMessage, String severity) {
        for (Observer o : observers) {
            o.update(alertCategory, vehicleRef, alertMessage, severity);
        }
    }

    public List<FuelEnergyLogDTO> getAll() {
        return dao.getAll();
    }

    public FuelEnergyLogDTO getById(int id) {
        return dao.getById(id);
    }

    public boolean add(FuelEnergyLogDTO log, String role) {
        if (!"OPERATOR".equalsIgnoreCase(role)) return false;
        boolean success = dao.add(log);
        if (success && log.getQuantityUsed() > 1) { // Example alert trigger
            notifyObservers("Fuel", log.getVehicleRef(), "High fuel usage: " + log.getQuantityUsed(), "CRITICAL");
        }
        return success;
    }

    public boolean update(FuelEnergyLogDTO log, String role) {
        if (!"OPERATOR".equalsIgnoreCase(role)) return false;
        boolean success = dao.update(log);
        if (success && log.getQuantityUsed() > 1) { // Example alert trigger
            notifyObservers("Fuel", log.getVehicleRef(), "High fuel usage: " + log.getQuantityUsed(), "CRITICAL");
        }
        return success;
    }

    public boolean delete(int id, String role) {
        if (!"OPERATOR".equalsIgnoreCase(role)) return false;
        return dao.delete(id);
    }
}