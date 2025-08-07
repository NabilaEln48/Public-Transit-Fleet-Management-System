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

/** This class that represents maintenance schedule instance.
 * @author Zhiru Xie
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.DTO
 */
public class MaintenanceScheduleDTO {
    /** Unique Identifier of the entity. */
    private int id;
    /** Unique Identifier of the component. */
    private int componentId;
    /** Unique Identifier of the vehicle. */
    private String vehicleId;
    /** Task description text. */
    private String taskDescription;
    /** Planned date for the maintenance. */
    private LocalDate plannedDate;
    /** The maintenance status. */
    private Enum_MaintenanceProgressStatus progressStatus;
    /** Unique Identifier of the technician. */
    private int technicianId;
    
    /**
     * Default constructor with one parameter.
     * @param id Unique Identifier of the entity.
    */
    public MaintenanceScheduleDTO(int id){this.id = id;}
    
    /**
     * Constructor with six parameters.
     * @param componentId Unique Identifier of the component
     * @param vehicleId Unique Identifier of the vehicle
     * @param taskDescription Task description text
     * @param plannedDate Planned date for the maintenance
     * @param progressStatus The maintenance status
     * @param technicianId Unique Identifier of the technician
    */
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
    
    /**
     * Constructor with seven parameters.
     * @param id Unique Identifier of the entity
     * @param componentId Unique Identifier of the component
     * @param vehicleId Unique Identifier of the vehicle
     * @param taskDescription Task description text
     * @param plannedDate Planned date for the maintenance
     * @param progressStatus The maintenance status
     * @param technicianId Unique Identifier of the technician
     */
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
    /** Getter for attribute id.
     * @return Value for attribute id
    */ 
    public int getId(){
        return this.id;
    }
    
    /** Getter for attribute componentId.
     * @return Value for attribute componentId
    */ 
    public int getComponentId(){
        return this.componentId;
    }
    
    /** Getter for attribute vehicleId.
     * @return Value for attribute vehicleId
    */ 
    public String getVehicleId() {
        return this.vehicleId;
    }
    
    /** Getter for attribute taskDescription.
     * @return Value for attribute taskDescription
    */ 
    public String getTaskDescription(){
        return this.taskDescription;
    }
    
    /** Getter for attribute plannedDate.
     * @return Value for attribute plannedDate
    */ 
    public LocalDate getPlannedDate(){
        return this.plannedDate;
    }
    
    /** Getter for attribute progressStatus.
     * @return Value for attribute progressStatus
    */ 
    public Enum_MaintenanceProgressStatus getProgressStatus(){
        return this.progressStatus;
    }
    
    /** Getter for attribute technicianId.
     * @return Value for attribute technicianId
    */ 
    public int getTechnicianId(){
        return this.technicianId;  
    }
    
    //Setters
    /**
     * Setter for attribute componentId.
     * @param componentId Value for attribute componentId
     */
    public void setComponentId(int componentId){
        this.componentId = componentId;
    }
    
    /**
     * Setter for attribute vehicleId.
     * @param vehicleId Value for attribute vehicleId
     */
    public void setVehicleId(String vehicleId){
        this.vehicleId = vehicleId;
    }
    
    /**
     * Setter for attribute taskDescription.
     * @param taskDescription Value for attribute taskDescription
     */
    public void setTaskDescription(String taskDescription){
        this.taskDescription = taskDescription;
    }
    
    /**
     * Setter for attribute plannedDate.
     * @param plannedDate Value for attribute plannedDate
     */
    public void setPlannedDate(LocalDate plannedDate){
        this.plannedDate = plannedDate;
    }
    
    /**
     * Setter for attribute progressStatus.
     * @param progressStatus Value for attribute progressStatus
     */
    public void setProgressStatus(Enum_MaintenanceProgressStatus progressStatus){
        this.progressStatus = progressStatus;
    }
    
    /**
     * Setter for attribute technicianId.
     * @param technicianId Value for attribute technicianId
     */
    public void setTechnicianId(int technicianId){
        this.technicianId = technicianId;
    }
}
