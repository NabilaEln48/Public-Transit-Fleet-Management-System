/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessLayer;

import transferObject.FuelUsageDTO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author simra
 */
public interface FuelUsageDAO {
    List<FuelUsageDTO> getAllEnergyUsage() throws SQLException;
    FuelUsageDTO getEnergyUsageById(int id) throws SQLException;
    List<FuelUsageDTO> getFilteredEnergyUsage(String energyType, String startDate, String endDate) throws SQLException;
    boolean addEnergyUsage(FuelUsageDTO usage) throws SQLException;
    boolean updateEnergyUsage(FuelUsageDTO usage) throws SQLException;
    boolean deleteEnergyUsage(int id) throws SQLException;
}