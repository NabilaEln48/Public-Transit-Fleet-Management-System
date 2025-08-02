/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessLayer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author 61963
 */
public class DataSource {
    /** Unique instance used for accessing the database. */
    private static Connection connection = null;
    
    /** Make the default constructor private to prevent other members from instantiating it. */
    private DataSource() { }
    
    /** Retrieve the database connection Singleton. Re-generate one if there's no one functioning.
     * @param username The username to access the database.
     * @param password The password to access the database.
    * @return The unique instance of database connection.
    */
    public static Connection getConnection(String username, String password) {
        DatabaseProperty props = new DatabaseProperty(username, password);
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(props.getUrl(), props.getUsername().isEmpty() ? "cst8288" : props.getUsername(), props.getPassword().isEmpty() ? "cst8288" : props.getPassword());
            } else {
                System.out.println("Cannot create new connection, using existing one");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch(ClassNotFoundException e){
            System.out.println("Driver not found: " + e.getMessage());
        }
        return connection;
    }
}