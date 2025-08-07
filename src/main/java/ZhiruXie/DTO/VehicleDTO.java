/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
 */
package ZhiruXie.DTO;

import ZhiruXie.enums.Enum_VehicleCategory;
import ZhiruXie.enums.Enum_VehicleOperationalState;

/** This class that represents vehicle instance.
 * @author Zhiru Xie
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.DTO
 */
public class VehicleDTO {
    /** Unique Identifier of the vehicle. */
    private String id;
    /** Vehicle type - Diesel, Electrical, or Hybrid.*/
    private Enum_VehicleCategory category;
    /** Vehicle registration number. */
    private String registrationNumber;
    /** Vehicle fuel consumption. */
    private String fuelUsed;
    /** Vehicle efficiency rate. */
    private double efficiencyRate;
    /** Vehicle fuel capacity. */
    private int capacity;
    /** Assigned route name. */
    private String assigned_route;
    /** Vehicle operational status - Active, Inactive, or Maintenance. */
    private Enum_VehicleOperationalState operationalState;
    
    /**
     * Default constructor with one parameter.
     * @param id Unique Identifier of the vehicle
    */
    public VehicleDTO(String id){
        this.id = id;
    }
    
    /**
     * Constructor with seven parameters.
     * @param category Vehicle type - Diesel, Electrical, or Hybrid
     * @param registrationNumber Vehicle registration number
     * @param fuelUsed Vehicle fuel consumption
     * @param efficiencyRate Vehicle efficiency rate
     * @param capacity Vehicle fuel capacity
     * @param assigned_route Assigned route name
     * @param operationalState Vehicle operational status - Active, Inactive, or Maintenance
     */
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
    
    /**
     * Constructor with eight parameters.
     * @param id Unique Identifier of the vehicle
     * @param category Vehicle type - Diesel, Electrical, or Hybrid
     * @param registrationNumber Vehicle registration number
     * @param fuelUsed Vehicle fuel consumption
     * @param efficiencyRate Vehicle efficiency rate
     * @param capacity Vehicle fuel capacity
     * @param assigned_route Assigned route name
     * @param operationalState Vehicle operational status - Active, Inactive, or Maintenance
     */
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

    /** Getter for attribute id.
     * @return Value for attribute id
    */ 
    public String getId() {
        return id;
    }

    /** Getter for attribute category.
     * @return Value for attribute category
    */ 
    public Enum_VehicleCategory getCategory() {
        return category;
    }

    /** Getter for attribute registrationNumber.
     * @return Value for attribute registrationNumber
    */ 
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /** Getter for attribute fuelUsed.
     * @return Value for attribute fuelUsed
    */ 
    public String getFuelUsed() {
        return fuelUsed;
    }

    /** Getter for attribute efficiencyRate.
     * @return Value for attribute efficiencyRate
    */ 
    public double getEfficiencyRate() {
        return efficiencyRate;
    }

    /** Getter for attribute efficiencyRate.
     * @return Value for attribute efficiencyRate
    */ 
    public int getCapacity() {
        return capacity;
    }

    /** Getter for attribute assigned_route.
     * @return Value for attribute assigned_route
    */ 
    public String getAssigned_route() {
        return assigned_route;
    }

    /** Getter for attribute operationalState.
     * @return Value for attribute operationalState
    */ 
    public Enum_VehicleOperationalState getOperationalState() {
        return operationalState;
    }

    // Setters

    /** Setter for attribute category.
     * @param category for attribute category
    */ 
    public void setCategory(Enum_VehicleCategory category) {
        this.category = category;
    }

    /** Setter for attribute registrationNumber.
     * @param registrationNumber for attribute registrationNumber
    */ 
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /** Setter for attribute fuelUsed.
     * @param fuelUsed for attribute fuelUsed
    */ 
    public void setFuelUsed(String fuelUsed) {
        this.fuelUsed = fuelUsed;
    }

    /** Setter for attribute efficiencyRate.
     * @param efficiencyRate for attribute efficiencyRate
    */ 
    public void setEfficiencyRate(double efficiencyRate) {
        this.efficiencyRate = efficiencyRate;
    }

    /** Setter for attribute capacity.
     * @param capacity for attribute capacity
    */ 
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /** Setter for attribute assigned_route.
     * @param assigned_route for attribute assigned_route
    */ 
    public void setAssigned_route(String assigned_route) {
        this.assigned_route = assigned_route;
    }

    /** Setter for attribute operationalState.
     * @param operationalState for attribute operationalState
    */ 
    public void setOperationalState(Enum_VehicleOperationalState operationalState) {
        this.operationalState = operationalState;
    }

    /** New rule for comparing between vehicle objects.
     * @param obj The comparison target.
    */ 
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        VehicleDTO other = (VehicleDTO) obj;
        return id != null ? id.equals(other.id) : other.id == null;
    }

    /**
     * New rule for calculating hash code for vehicle objects.
     * @return hash result
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
