/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZhiruXie.Utility;

/**
 *
 * @author 61963
 */
import ZhiruXie.DTO.PerformanceDTO;
import java.time.LocalDateTime;

public class PerformanceDTOBuilder {
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

    public PerformanceDTOBuilder id(int id) {
        this.id = id;
        return this;
    }

    public PerformanceDTOBuilder operatorId(int operatorId) {
        this.operatorId = operatorId;
        return this;
    }

    public PerformanceDTOBuilder vehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public PerformanceDTOBuilder routeName(String routeName) {
        this.routeName = routeName;
        return this;
    }

    public PerformanceDTOBuilder startTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public PerformanceDTOBuilder endTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public PerformanceDTOBuilder scheduledStartTime(LocalDateTime scheduledStartTime) {
        this.scheduledStartTime = scheduledStartTime;
        return this;
    }

    public PerformanceDTOBuilder onTime(boolean onTime) {
        this.onTime = onTime;
        return this;
    }

    public PerformanceDTOBuilder distance(double distance) {
        this.distance = distance;
        return this;
    }

    public PerformanceDTOBuilder usedTime(int usedTime) {
        this.usedTime = usedTime;
        return this;
    }

    public PerformanceDTOBuilder idleTime(int idleTime) {
        this.idleTime = idleTime;
        return this;
    }

    public PerformanceDTOBuilder fuelSpent(double fuelSpent) {
        this.fuelSpent = fuelSpent;
        return this;
    }

    public PerformanceDTOBuilder passengerNumber(int passengerNumber) {
        this.passengerNumber = passengerNumber;
        return this;
    }

    public PerformanceDTO buildComplete() {
        PerformanceDTO dto = new PerformanceDTO();
        dto.setId(this.id);
        dto.setOperatorId(this.operatorId);
        dto.setVehicleId(this.vehicleId);
        dto.setRouteName(this.routeName);
        dto.setStartTime(this.startTime);
        dto.setEndTime(this.endTime);
        dto.setScheduledStartTime(this.scheduledStartTime);
        dto.setOnTime(this.onTime);
        dto.setDistance(this.distance);
        dto.setUsedTime(this.usedTime);
        dto.setIdleTime(this.idleTime);
        dto.setFuelSpent(this.fuelSpent);
        dto.setPassengerNumber(this.passengerNumber);
        return dto;
    }
    
    public PerformanceDTO buildWithoutId() {
        PerformanceDTO dto = new PerformanceDTO();
        dto.setOperatorId(this.operatorId);
        dto.setVehicleId(this.vehicleId);
        dto.setRouteName(this.routeName);
        dto.setStartTime(this.startTime);
        dto.setEndTime(this.endTime);
        dto.setScheduledStartTime(this.scheduledStartTime);
        dto.setOnTime(this.onTime);
        dto.setDistance(this.distance);
        dto.setUsedTime(this.usedTime);
        dto.setIdleTime(this.idleTime);
        dto.setFuelSpent(this.fuelSpent);
        dto.setPassengerNumber(this.passengerNumber);
        return dto;
    }
    
    public PerformanceDTO buildOnlyId() {
        PerformanceDTO dto = new PerformanceDTO();
        dto.setId(this.id);
        return dto;
    }
}
