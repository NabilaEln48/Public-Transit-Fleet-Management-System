package com.algonquincollege.simran_final_project.dataaccesslayer;

import com.algonquincollege.simran_final_project.transferobject.FuelEnergyLogDTO;
import java.util.List;

/**
 * DAO interface for fuel_energy_logs operations.
 */
public interface FuelEnergyLogDAO {
    List<FuelEnergyLogDTO> getLogsByVehicle(String vehicleRef) throws Exception;
    void insertLog(FuelEnergyLogDTO log) throws Exception;
}
