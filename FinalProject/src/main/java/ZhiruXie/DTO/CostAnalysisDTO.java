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

/** This class that represents cost analysis report instance.
 * @author Zhiru Xie
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.DTO
 */
public class CostAnalysisDTO {
    /** Unique Identifier of the entity. */
    private int id;
    /** Report type - Fuel Usage or Maintenance Cost. */
    private String type;
    /** A combined text content that contains the Vehicle Name and the consumption quantity. */
    private String payload;
    /** The reports creation date. */
    private LocalDateTime dateOfCreate;
    
    /**
     * Default constructor with one parameter.
     * @param id Unique Identifier of the entity.
    */
    public CostAnalysisDTO(int id) {
        this.id = id;
    }
    
    /**
     * Constructor with three parameters.
     * @param type Report type - Fuel Usage or Maintenance Cost.
     * @param payload A combined text content that contains the Vehicle Name and the consumption quantity.
     * @param dateOfCreate The reports creation date.
    */
    public CostAnalysisDTO(
            String type,
            String payload,
            LocalDateTime dateOfCreate
    ) {
        this.type = type;
        this.payload = payload;
        this.dateOfCreate = dateOfCreate;
    }
    
    /**
     * Constructor with four parameters.
     * @param id Unique Identifier of the entity.
     * @param type Report type - Fuel Usage or Maintenance Cost.
     * @param payload A combined text content that contains the Vehicle Name and the consumption quantity.
     * @param dateOfCreate The reports creation date.
    */
    public CostAnalysisDTO(
            int id,
            String type,
            String payload,
            LocalDateTime dateOfCreate
    ) {
        this(type, payload, dateOfCreate);
        this.id = id;
    }
    
    /** Getter for attribute id.
     * @return Value for attribute id
    */
    public int getId(){
        return this.id;
    } 
    
    /**
     * Getter for attribute type.
     * @return Value for attribute type
     */
    public String getType(){
        return this.type;
    }
    
    /**
     * Getter for attribute payload.
     * @return Value for attribute payload
     */
    public String getPayload(){
        return this.payload;
    }
    
    /**
     * Getter for attribute dateOfCreate.
     * @return Value for attribute dateOfCreate
     */
    public LocalDateTime getDateOfCreate(){
        return this.dateOfCreate;
    }
    
    /**
     * Setter for attribute dateOfCreate.
     * @param type Value for attribute type
     */
    public void setType(String type){
        this.type = type;
    }
    
    /**
     * Setter for attribute dateOfCreate.
     * @param payload Value for attribute payload
     */
    public void setPayload(String payload){
        this.payload = payload;
    }
        
    /**
     * Setter for attribute dateOfCreate.
     * @param dateOfCreate Value for attribute dateOfCreate
     */
    public void setDateOfCreate(LocalDateTime dateOfCreate){
        this.dateOfCreate = dateOfCreate;
    }
}
