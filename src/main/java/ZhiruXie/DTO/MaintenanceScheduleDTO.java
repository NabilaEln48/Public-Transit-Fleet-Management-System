/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
 */
package ZhiruXie.DTO;

import java.time.LocalDate;
import ZhiruXie.enums.Enum_MaintenanceProgressStatus;

/**
 *
 * @author 61963
 */
public class MaintenanceScheduleDTO {
    private int id;
    private int componentId;
    private String vehicleId;
    private String taskDescription;
    private LocalDate plannedDate;
    private Enum_MaintenanceProgressStatus progressStatus;
    private int technicianId;
    
    public MaintenanceScheduleDTO(int id){this.id = id;}
    
    public MaintenanceScheduleDTO(
            int componentId,
            String vehicleId,
            String taskDescription,
            LocalDate plannedDate,
            Enum_MaintenanceProgressStatus progressStatus,
            int technicianId){
        this.componentId = componentId;
        this.vehicleId = vehicleId;
        this.taskDescription = taskDescription;
        this.plannedDate = plannedDate;
        this.progressStatus = progressStatus;
        this.technicianId = technicianId;
    }
    
    public MaintenanceScheduleDTO(
            int id,
            int componentId,
            String vehicleId,
            String taskDescription,
            LocalDate plannedDate,
            Enum_MaintenanceProgressStatus progressStatus,
            int technicianId
    ){
        this(
                componentId,
                vehicleId,
                taskDescription,
                plannedDate,
                progressStatus,
                technicianId
        );
        this.id = id;
    }
    
    //Getters
    
    public int getId(){
        return this.id;
    }
    
    public int getComponentId(){
        return this.componentId;
    }
    
    public String getVehicleId() {
        return this.vehicleId;
    }
    
    public String getTaskDescription(){
        return this.taskDescription;
    }
    
    public LocalDate getPlannedDate(){
        return this.plannedDate;
    }
    
    public Enum_MaintenanceProgressStatus getProgressStatus(){
        return this.progressStatus;
    }
    
    public int getTechnicianId(){
        return this.technicianId;  
    }
    
    //Setters
    
    public void setComponentId(int componentId){
        this.componentId = componentId;
    }
    
    public void setVehicleId(String vehicleId){
        this.vehicleId = vehicleId;
    }
    
    public void setTaskDescription(String taskDescription){
        this.taskDescription = taskDescription;
    }
    
    public void setPlannedDate(LocalDate plannedDate){
        this.plannedDate = plannedDate;
    }
    
    public void setProgressStatus(Enum_MaintenanceProgressStatus progressStatus){
        this.progressStatus = progressStatus;
    }
    
    public void setTechnicianId(int technicianId){
        this.technicianId = technicianId;
    }
    
    @Override
    public String toString(){
        return String.format(
                "Maintenance Schedule Id: %d%n" +
                "ComponentId: %d%n" +
                "VehicleId: %s%n" + 
                "Task Description: %s%n" +
                "Planned Date: %s%n" +
                "Status: %s%n",this.id,this.componentId,this.vehicleId,this.taskDescription,this.plannedDate,this.progressStatus);
    }
}
