/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
 */
package ZhiruXie.Utility;

import ZhiruXie.DTO.PerformanceDTO;
import java.time.LocalDateTime;

/** This builder for performance report to mitigate telescoping constructor
 * @author Zhiru Xie
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.Utility
 */
public class PerformanceDTOBuilder {
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

    /** Build attribute id.
     * @param id for attribute id
     * @return The builder instance after attaching id
    */
    public PerformanceDTOBuilder id(int id) {
        this.id = id;
        return this;
    }

    /** Build attribute operatorId.
     * @param operatorId for attribute operatorId
     * @return The builder instance after attaching operatorId
    */
    public PerformanceDTOBuilder operatorId(int operatorId) {
        this.operatorId = operatorId;
        return this;
    }

    /** Build attribute vehicleId.
     * @param vehicleId for attribute vehicleId
     * @return The builder instance after attaching vehicleId
    */
    public PerformanceDTOBuilder vehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    /** Build attribute routeName.
     * @param routeName for attribute routeName
     * @return The builder instance after attaching routeName
    */
    public PerformanceDTOBuilder routeName(String routeName) {
        this.routeName = routeName;
        return this;
    }

    /** Build attribute startTime.
     * @param startTime for attribute startTime
     * @return The builder instance after attaching startTime
    */
    public PerformanceDTOBuilder startTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    /** Build attribute endTime.
     * @param endTime for attribute endTime
     * @return The builder instance after attaching endTime
    */
    public PerformanceDTOBuilder endTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    /** Build attribute scheduledStartTime.
     * @param scheduledStartTime for attribute scheduledStartTime
     * @return The builder instance after attaching scheduledStartTime
    */
    public PerformanceDTOBuilder scheduledStartTime(LocalDateTime scheduledStartTime) {
        this.scheduledStartTime = scheduledStartTime;
        return this;
    }

    /** Build attribute onTime.
     * @param onTime for attribute onTime
     * @return The builder instance after attaching onTime
    */
    public PerformanceDTOBuilder onTime(boolean onTime) {
        this.onTime = onTime;
        return this;
    }

    /** Build attribute distance.
     * @param distance for attribute distance
     * @return The builder instance after attaching distance
    */
    public PerformanceDTOBuilder distance(double distance) {
        this.distance = distance;
        return this;
    }

    /** Build attribute usedTime.
     * @param usedTime for attribute usedTime
     * @return The builder instance after attaching usedTime
    */
    public PerformanceDTOBuilder usedTime(int usedTime) {
        this.usedTime = usedTime;
        return this;
    }

    /** Build attribute idleTime.
     * @param idleTime for attribute idleTime
     * @return The builder instance after attaching idleTime
    */
    public PerformanceDTOBuilder idleTime(int idleTime) {
        this.idleTime = idleTime;
        return this;
    }
    
    /** Build attribute fuelSpent.
     * @param fuelSpent for attribute fuelSpent
     * @return The builder instance after attaching fuelSpent
    */
    public PerformanceDTOBuilder fuelSpent(double fuelSpent) {
        this.fuelSpent = fuelSpent;
        return this;
    }
    
    /** Build attribute passengerNumber.
     * @param passengerNumber for attribute passengerNumber
     * @return The builder instance after attaching passengerNumber
    */
    public PerformanceDTOBuilder passengerNumber(int passengerNumber) {
        this.passengerNumber = passengerNumber;
        return this;
    }

    /** Build all attributes.
     * @return The builder instance after attaching all attributes
    */
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
    
    /** Build all attributes except id.
     * @return The builder instance after attaching all attributes except id
    */
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
    
    /** Build only attributes id.
     * @return The builder instance after attaching attribute id
    */
    public PerformanceDTO buildOnlyId() {
        PerformanceDTO dto = new PerformanceDTO();
        dto.setId(this.id);
        return dto;
    }
}
