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

/**
 *
 * @author 61963
 */
public class PerformanceDTO {
    private int id;
    private int operatorId;
    private String vehicleId;
    private String routeName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime scheduledStartTime;
    private boolean onTime;
    private double distance;
    private int usedTime;
    private int idleTime;
    private double fuelSpent;
    private int passengerNumber;
    
    public PerformanceDTO(){}
    public PerformanceDTO(int id){this.id = id;}
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
    
    public int getId(){
        return this.id;
    }
    
    public int getOperatorId(){
        return this.operatorId;
    }
    
    public void setOperatorId(int operatorId){
        this.operatorId = operatorId;
    }
    
    public String getVehicleId(){
        return this.vehicleId;
    }
    
    public void setVehicleId(String vehicleId){
        this.vehicleId = vehicleId;
    }
    
    public String getRouteName(){
        return this.routeName;
    }
    
    public void setRouteName(String routeName){
        this.routeName = routeName;
    }
    
    public LocalDateTime getStartTime(){
        return this.startTime;
    }
    
    public void setStartTime(LocalDateTime startTime){
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime(){
        return this.endTime;
    }
    
    public void setEndTime(LocalDateTime endTime){
        this.endTime = endTime;
    }
    
    public LocalDateTime getScheduledStartTime(){
        return this.scheduledStartTime;
    }
    
    public void setScheduledStartTime(LocalDateTime scheduledStartTime){
        this.scheduledStartTime = scheduledStartTime;
    }
    
    public boolean getOnTime(){
        return this.onTime;
    }
    
    public void setOnTime(boolean onTime){
        this.onTime = onTime;
    }
    
    public double getDistance(){
        return this.distance;
    }
    
    public void setDistance(double distance){
        this.distance = distance;
    }
    
    public int getUsedTime(){
        return this.usedTime;
    }
    
    public void setUsedTime(int usedTime){
        this.usedTime = usedTime;
    }
    
    public int getIdleTime(){
        return this.idleTime;
    }
    
    public void setIdleTime(int idleTime){
        this.idleTime = idleTime;
    }
    
    public double getFuelSpent(){
        return this.fuelSpent;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setFuelSpent(double fuelSpent){
        this.fuelSpent = fuelSpent;
    }
    
    public int getPassengerNumber(){
        return this.passengerNumber;
    }
    
    public void setPassengerNumber(int passengerNumber){
        this.passengerNumber = passengerNumber;
    }
}
