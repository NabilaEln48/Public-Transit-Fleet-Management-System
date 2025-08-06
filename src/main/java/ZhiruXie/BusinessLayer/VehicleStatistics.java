package ZhiruXie.BusinessLayer;

/**
 * Vehicle statistics data class
 * Contains aggregated vehicle information for reporting
 *
 * @author Purnima
 */
public class VehicleStatistics {
    private int totalVehicles;
    private int activeVehicles;
    private int maintenanceVehicles;
    private int inactiveVehicles;
    private int dieselBuses;
    private int electricRail;
    private int dieselTrains;
    
    public VehicleStatistics(int totalVehicles, int activeVehicles, int maintenanceVehicles,
                           int inactiveVehicles, int dieselBuses, int electricRail, int dieselTrains) {
        this.totalVehicles = totalVehicles;
        this.activeVehicles = activeVehicles;
        this.maintenanceVehicles = maintenanceVehicles;
        this.inactiveVehicles = inactiveVehicles;
        this.dieselBuses = dieselBuses;
        this.electricRail = electricRail;
        this.dieselTrains = dieselTrains;
    }
    
    // Getters and Setters
    public int getTotalVehicles() {
        return totalVehicles;
    }
    
    public void setTotalVehicles(int totalVehicles) {
        this.totalVehicles = totalVehicles;
    }
    
    public int getActiveVehicles() {
        return activeVehicles;
    }
    
    public void setActiveVehicles(int activeVehicles) {
        this.activeVehicles = activeVehicles;
    }
    
    public int getMaintenanceVehicles() {
        return maintenanceVehicles;
    }
    
    public void setMaintenanceVehicles(int maintenanceVehicles) {
        this.maintenanceVehicles = maintenanceVehicles;
    }
    
    public int getInactiveVehicles() {
        return inactiveVehicles;
    }
    
    public void setInactiveVehicles(int inactiveVehicles) {
        this.inactiveVehicles = inactiveVehicles;
    }
    
    public int getDieselBuses() {
        return dieselBuses;
    }
    
    public void setDieselBuses(int dieselBuses) {
        this.dieselBuses = dieselBuses;
    }
    
    public int getElectricRail() {
        return electricRail;
    }
    
    public void setElectricRail(int electricRail) {
        this.electricRail = electricRail;
    }
    
    public int getDieselTrains() {
        return dieselTrains;
    }
    
    public void setDieselTrains(int dieselTrains) {
        this.dieselTrains = dieselTrains;
    }
    
    @Override
    public String toString() {
        return "VehicleStatistics{" +
                "totalVehicles=" + totalVehicles +
                ", activeVehicles=" + activeVehicles +
                ", maintenanceVehicles=" + maintenanceVehicles +
                ", inactiveVehicles=" + inactiveVehicles +
                ", dieselBuses=" + dieselBuses +
                ", electricRail=" + electricRail +
                ", dieselTrains=" + dieselTrains +
                '}';
    }
}