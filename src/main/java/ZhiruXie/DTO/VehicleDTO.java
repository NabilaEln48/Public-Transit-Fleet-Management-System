package ZhiruXie.DTO;

import ZhiruXie.enums.Enum_VehicleCategory;
import ZhiruXie.enums.Enum_VehicleOperationalState;

public class VehicleDTO {
    private String id;
    private String vehicleNumber; // <-- ADD THIS FIELD
    private Enum_VehicleCategory category;
    private String registrationNumber;
    private String fuelUsed;
    private double efficiencyRate;
    private int capacity;
    private String assigned_route;
    private Enum_VehicleOperationalState operationalState;
    
    // Default constructor (important for some frameworks, or if you use setters)
    public VehicleDTO() {}

    // Constructor with ID (if you use it to fetch by ID)
    public VehicleDTO(String id){
        this.id = id;
    }
    
    // Constructor for adding new vehicles (without ID, as it might be auto-generated or set later)
    public VehicleDTO(
            String vehicleNumber, // <-- ADD THIS TO CONSTRUCTOR
            Enum_VehicleCategory category,
            String registrationNumber,
            String fuelUsed,
            double efficiencyRate,
            int capacity,
            String assigned_route,
            Enum_VehicleOperationalState operationalState
    ){
        this.vehicleNumber = vehicleNumber; // Set it here
        this.category = category;
        this.registrationNumber = registrationNumber;
        this.fuelUsed = fuelUsed;
        this.efficiencyRate = efficiencyRate;
        this.capacity = capacity;
        this.assigned_route = assigned_route;
        this.operationalState = operationalState;
    }
    
    // Full constructor (for retrieving all fields, including ID)
    public VehicleDTO(
            String id,
            String vehicleNumber, // <-- ADD THIS TO CONSTRUCTOR
            Enum_VehicleCategory category,
            String registrationNumber,
            String fuelUsed,
            double efficiencyRate,
            int capacity,
            String assigned_route,
            Enum_VehicleOperationalState operationalState
    ){
        this(vehicleNumber, category, registrationNumber, fuelUsed, efficiencyRate, capacity, assigned_route, operationalState);
        this.id = id;
    }
    
    // Getters
    public String getId() {
        return id;
    }

    public String getVehicleNumber() { // <-- ADD THIS GETTER
        return vehicleNumber;
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
    public void setId(String id) { // <-- ADD THIS SETTER if you use default constructor
        this.id = id;
    }

    public void setVehicleNumber(String vehicleNumber) { // <-- ADD THIS SETTER
        this.vehicleNumber = vehicleNumber;
    }

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