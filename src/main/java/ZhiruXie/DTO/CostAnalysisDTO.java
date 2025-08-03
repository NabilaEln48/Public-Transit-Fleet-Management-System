/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZhiruXie.DTO;

import java.time.LocalDateTime;

/**
 *
 * @author 61963
 */
public class CostAnalysisDTO {
    private int id;
    private String type;
    private String payload;
    private LocalDateTime dateOfCreate;
    
    public CostAnalysisDTO(int id) {
        this.id = id;
    }
    
    public CostAnalysisDTO(
            String type,
            String payload,
            LocalDateTime dateOfCreate
    ) {
        this.type = type;
        this.payload = payload;
        this.dateOfCreate = dateOfCreate;
    }
    
    public CostAnalysisDTO(
            int id,
            String type,
            String vehicleId,
            LocalDateTime dateOfCreate
    ) {
        this(type, vehicleId, dateOfCreate);
        this.id = id;
    }
    
    public int getId(){
        return this.id;
    } 
    
    public String getType(){
        return this.type;
    }
    
    public String getPayload(){
        return this.payload;
    }
    
    public LocalDateTime getDateOfCreate(){
        return this.dateOfCreate;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public void setPayload(String payload){
        this.payload = payload;
    }
        
    public void setDateOfCreate(LocalDateTime dateOfCreate){
        this.dateOfCreate = dateOfCreate;
    }
}
