/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZhiruXie.DTO;

import ZhiruXie.enums.Enum_VehicleCategory;
import ZhiruXie.enums.Enum_VehicleOperationalState;

public class VehicleDTO {
    private String id;
    private Enum_VehicleCategory category;
    private String registrationNumber;
    private String fuelUsed;
    private double efficiencyRate;
    private int capacity;
    private String assigned_route;
    private Enum_VehicleOperationalState operationalState;
    
    public VehicleDTO(String id){
        this.id = id;
    }
    
    public VehicleDTO(
            Enum_VehicleCategory category,
            String registrationNumber,
            String fuelUsed,
            double efficiencyRate,
            int capacity,
            String assigned_route,
            Enum_VehicleOperationalState operationalState
    ){
        this.category = category;
        this.registrationNumber = registrationNumber;
        this.fuelUsed = fuelUsed;
        this.efficiencyRate = efficiencyRate;
        this.capacity = capacity;
        this.assigned_route = assigned_route;
        this.operationalState = operationalState;
    }
    
    public VehicleDTO(
            String id,
            Enum_VehicleCategory category,
            String registrationNumber,
            String fuelUsed,
            double efficiencyRate,
            int capacity,
            String assigned_route,
            Enum_VehicleOperationalState operationalState
    ){
        this(category, registrationNumber, fuelUsed, efficiencyRate, capacity, assigned_route, operationalState);
        this.id = id;
    }
    
    // Getters
    public String getId() {
        return id;
    }

    public Enum_VehicleCategory getCategory() {
        return category;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getFuelUsed() {
        return fuelUsed;
    }

    public double getEfficiencyRate() {
        return efficiencyRate;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getAssigned_route() {
        return assigned_route;
    }

    public Enum_VehicleOperationalState getOperationalState() {
        return operationalState;
    }

    // Setters
    public void setCategory(Enum_VehicleCategory category) {
        this.category = category;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setFuelUsed(String fuelUsed) {
        this.fuelUsed = fuelUsed;
    }

    public void setEfficiencyRate(double efficiencyRate) {
        this.efficiencyRate = efficiencyRate;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setAssigned_route(String assigned_route) {
        this.assigned_route = assigned_route;
    }

    public void setOperationalState(Enum_VehicleOperationalState operationalState) {
        this.operationalState = operationalState;
    }
}
