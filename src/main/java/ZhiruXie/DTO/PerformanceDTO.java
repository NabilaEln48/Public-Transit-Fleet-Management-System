/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
 */
package ZhiruXie.DTO;

import java.time.LocalDateTime;

/** This class that represents performance report instance.
 * @author Zhiru Xie
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.DTO
 */
public class PerformanceDTO {
    /** Unique Identifier of the entity. */
    private int id;
    /** Unique Identifier of the operator. */
    private int operatorId;
    /** Unique Identifier of the vehicle. */
    private String vehicleId;
    /** Name of the assigned route. */
    private String routeName;
    /** Start time of the trip. */
    private LocalDateTime startTime;
    /** End time of the trip. */
    private LocalDateTime endTime;
    /** Planned start time of the trip. */
    private LocalDateTime scheduledStartTime;
    /** Flag for departure punctuality. */
    private boolean onTime;
    /** Trip distance in kilometer. */
    private double distance;
    /** Trip time consumption in minute. */
    private int usedTime;
    /** Idle time during the trip in minute. */
    private int idleTime;
    /** Fuel consumption unit for the trip. */
    private double fuelSpent;
    /** Number of passengers. */
    private int passengerNumber;
    
    /**
     * Default constructor with no parameter.
     */
    public PerformanceDTO(){}

    /**
     * Constructor with one parameter.
     * @param id Unique Identifier of the entity
     */
    public PerformanceDTO(int id){this.id = id;}

    /**
     * Constructor with twelve parameters.
     * @param operatorId Unique Identifier of the operator
     * @param vehicleId Unique Identifier of the vehicle
     * @param routeName Name of the assigned route
     * @param startTime Start time of the trip
     * @param endTime End time of the trip
     * @param scheduledStartTime Planned start time of the trip
     * @param onTime Flag for departure punctuality
     * @param distance Trip distance in kilometer
     * @param usedTime Trip time consumption in minute
     * @param idleTime Idle time during the trip in minute
     * @param fuelSpent Fuel consumption unit for the trip
     * @param passengerNumber Number of passengers
     */
    public PerformanceDTO(
            int operatorId,
            String vehicleId,
            String routeName,
            LocalDateTime startTime,
            LocalDateTime endTime,
            LocalDateTime scheduledStartTime,
            boolean onTime,
            double distance,
            int usedTime,
            int idleTime,
            double fuelSpent,
            int passengerNumber
    ){
        this.operatorId = operatorId;
        this.vehicleId = vehicleId;
        this.routeName = routeName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.scheduledStartTime = scheduledStartTime;
        this.onTime = onTime;
        this.distance = distance;
        this.usedTime = usedTime;
        this.idleTime = idleTime;
        this.fuelSpent = fuelSpent;
        this.passengerNumber = passengerNumber;
    }

    /**
     * Constructor with thirteen parameters.
     * @param id Unique Identifier of the entity
     * @param operatorId Unique Identifier of the operator
     * @param vehicleId Unique Identifier of the vehicle
     * @param routeName Name of the assigned route
     * @param startTime Start time of the trip
     * @param endTime End time of the trip
     * @param scheduledStartTime Planned start time of the trip
     * @param onTime Flag for departure punctuality
     * @param distance Trip distance in kilometer
     * @param usedTime Trip time consumption in minute
     * @param idleTime Idle time during the trip in minute
     * @param fuelSpent Fuel consumption unit for the trip
     * @param passengerNumber Number of passengers
     */
    public PerformanceDTO(
            int id,
            int operatorId,
            String vehicleId,
            String routeName,
            LocalDateTime startTime,
            LocalDateTime endTime,
            LocalDateTime scheduledStartTime,
            boolean onTime,
            double distance,
            int usedTime,
            int idleTime,
            double fuelSpent,
            int passengerNumber
    ){
        this(operatorId,vehicleId,routeName,startTime,endTime,scheduledStartTime,onTime,distance,usedTime,idleTime,fuelSpent,passengerNumber);
        this.id = id;
    }
    
    /** Getter for attribute id.
     * @return Value for attribute id
    */ 
    public int getId(){
        return this.id;
    }
    
    /** Getter for attribute operatorId.
     * @return Value for attribute operatorId
    */ 
    public int getOperatorId(){
        return this.operatorId;
    }
    
    /** Setter for attribute operatorId.
     * @param operatorId for attribute operatorId
    */ 
    public void setOperatorId(int operatorId){
        this.operatorId = operatorId;
    }
    
    /** Getter for attribute vehicleId.
     * @return Value for attribute vehicleId
    */ 
    public String getVehicleId(){
        return this.vehicleId;
    }
    
    /** Setter for attribute vehicleId.
     * @param vehicleId for attribute vehicleId
    */ 
    public void setVehicleId(String vehicleId){
        this.vehicleId = vehicleId;
    }
    
    /** Getter for attribute routeName.
     * @return Value for attribute routeName
    */ 
    public String getRouteName(){
        return this.routeName;
    }
    
    /** Setter for attribute routeName.
     * @param routeName for attribute routeName
    */
    public void setRouteName(String routeName){
        this.routeName = routeName;
    }
    
    /** Getter for attribute startTime.
     * @return Value for attribute startTime
    */ 
    public LocalDateTime getStartTime(){
        return this.startTime;
    }
    
    /** Setter for attribute startTime.
     * @param startTime for attribute startTime
    */
    public void setStartTime(LocalDateTime startTime){
        this.startTime = startTime;
    }
    
    /** Getter for attribute endTime.
     * @return Value for attribute endTime
    */ 
    public LocalDateTime getEndTime(){
        return this.endTime;
    }
    
    /** Setter for attribute endTime.
     * @param endTime for attribute endTime
    */
    public void setEndTime(LocalDateTime endTime){
        this.endTime = endTime;
    }
    
    /** Getter for attribute scheduledStartTime.
     * @return Value for attribute scheduledStartTime
    */ 
    public LocalDateTime getScheduledStartTime(){
        return this.scheduledStartTime;
    }
    
    /** Setter for attribute scheduledStartTime.
     * @param scheduledStartTime for attribute scheduledStartTime
    */
    public void setScheduledStartTime(LocalDateTime scheduledStartTime){
        this.scheduledStartTime = scheduledStartTime;
    }
    
    /** Getter for attribute onTime.
     * @return Value for attribute onTime
    */ 
    public boolean getOnTime(){
        return this.onTime;
    }
    
    /** Setter for attribute onTime.
     * @param onTime for attribute onTime
    */
    public void setOnTime(boolean onTime){
        this.onTime = onTime;
    }
    
    /** Getter for attribute distance.
     * @return Value for attribute distance
    */ 
    public double getDistance(){
        return this.distance;
    }
    
    /** Setter for attribute distance.
     * @param distance for attribute distance
    */
    public void setDistance(double distance){
        this.distance = distance;
    }
    
    /** Getter for attribute usedTime.
     * @return Value for attribute usedTime
    */ 
    public int getUsedTime(){
        return this.usedTime;
    }
    
    /** Setter for attribute usedTime.
     * @param usedTime for attribute usedTime
    */
    public void setUsedTime(int usedTime){
        this.usedTime = usedTime;
    }
    
    /** Getter for attribute idleTime.
     * @return Value for attribute idleTime
    */ 
    public int getIdleTime(){
        return this.idleTime;
    }
    
    /** Setter for attribute idleTime.
     * @param idleTime for attribute idleTime
    */
    public void setIdleTime(int idleTime){
        this.idleTime = idleTime;
    }
    
    /** Getter for attribute fuelSpent.
     * @return Value for attribute fuelSpent
    */ 
    public double getFuelSpent(){
        return this.fuelSpent;
    }
    
    /** Setter for attribute id.
     * @param id for attribute id
    */
    public void setId(int id){
        this.id = id;
    }
    
    /** Setter for attribute fuelSpent.
     * @param fuelSpent for attribute fuelSpent
    */
    public void setFuelSpent(double fuelSpent){
        this.fuelSpent = fuelSpent;
    }
    
    /** Getter for attribute passengerNumber.
     * @return Value for attribute passengerNumber
    */ 
    public int getPassengerNumber(){
        return this.passengerNumber;
    }
    
    /** Setter for attribute passengerNumber.
     * @param passengerNumber for attribute passengerNumber
    */
    public void setPassengerNumber(int passengerNumber){
        this.passengerNumber = passengerNumber;
    }
}
