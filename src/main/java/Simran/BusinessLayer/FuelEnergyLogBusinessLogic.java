/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Simran.BusinessLayer;

/**
 *
 * @author simra
 */
import Simran.dataaccesslayer.FuelEnergyLogDAO;
import Simran.dataaccesslayer.FuelEnergyLogDAOImpl;
import Simran.transferobject.FuelEnergyLogDTO;
import java.util.List;

public class FuelEnergyLogBusinessLogic {
    private final FuelEnergyLogDAO dao = new FuelEnergyLogDAOImpl();

    public List<FuelEnergyLogDTO> getAll() {
        return dao.getAll();
    }

    public boolean add(FuelEnergyLogDTO log, String role) {
        if (!"OPERATOR".equalsIgnoreCase(role)) return false;
        return dao.add(log);
    }

    public boolean update(FuelEnergyLogDTO log, String role) {
        if (!"OPERATOR".equalsIgnoreCase(role)) return false;
        return dao.update(log);
    }

    public boolean delete(int id, String role) {
        if (!"OPERATOR".equalsIgnoreCase(role)) return false;
        return dao.delete(id);
    }
}