/*
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : DAO (Data Access Object) class for the User entity.
                   Provides methods to insert a new user and authenticate a user
                   using JDBC operations on the USER table.
*/

package com.group.project.nabila.msiah.DataAccessLayer;

import com.group.project.nabila.msiah.DTOLayer.User;
import java.sql.*;

/**
 * Handles all database operations related to the User entity.
 * Provides methods for user registration and authentication.
 */
public class UserDAO {

    private final Connection conn;

    /**
     * Constructor that initializes the DAO with a JDBC connection.
     *
     * @param conn the active database connection
     */
    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new user into the USER table in the database.
     *
     * @param user the User object containing name, email, hashed password, and role
     * @return true if the user is successfully inserted, false otherwise
     */
    public boolean insertUser(User user) {
        String sql = "INSERT INTO USER (NAME, EMAIL, PASSWORD, USER_TYPE) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword()); // hashed
            stmt.setString(4, user.getRole());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Authenticates a user by checking their email and hashed password against the database.
     *
     * @param email the user's email
     * @param hashedPassword the user's hashed password
     * @return a User object if authentication succeeds, null otherwise
     */
    public User authenticate(String email, String hashedPassword) {
        String sql = "SELECT * FROM USER WHERE EMAIL = ? AND PASSWORD = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, hashedPassword);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("USER_ID"),
                    rs.getString("NAME"),
                    rs.getString("EMAIL"),
                    rs.getString("PASSWORD"),
                    rs.getString("USER_TYPE")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
