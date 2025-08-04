/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Simran.dataaccesslayer;

import Simran.transferobject.FuelEnergyLogDTO;
import java.util.List;

public interface FuelEnergyLogDAO {
    List<FuelEnergyLogDTO> getAll();
    FuelEnergyLogDTO getById(int id);
    boolean add(FuelEnergyLogDTO log);
    boolean update(FuelEnergyLogDTO log);
    boolean delete(int id);
}
