/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessLayer;

/**
 *
 * @author simra
 */
public class DatabaseProperty {
    /** The url of the database. */
    private static final String URL="jdbc:mysql://localhost:3306/ptfms_db";
    /** The username for accessing the database. */
    private String userName;
    /** The password for accessing the database. */
    private String password;
    
    /** Constructor with two parameters, username and password.
     * @param userName User input username for accessing the database.
     * @param password User input password for accessing the database.
     */
    public DatabaseProperty(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
    
    /** Getter for attribute url.
     * @return The url of the database.
     */
    public String getUrl(){
        return URL;
    }
    
    /** Getter for attribute userName.
     * @return The username for accessing the database.
     */
    public String getUsername(){
        return this.userName;
    }
    
    public void setUsername(String userName){
        this.userName = userName;
    }
    
    /** Getter for attribute password.
     * @return The password for accessing the database.
     */
    public String getPassword(){
        return this.password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
}