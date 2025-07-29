/*
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : DAO (Data Access Object) class for the User entity.
                   This class provides methods to insert users and authenticate credentials
                   by interacting with the database using JDBC.
*/

package com.group.project.nabila.msiah.DataAccessLayer;

import com.group.project.nabila.msiah.DTOLayer.User;
import java.sql.*;

/**
 * UserDAO is responsible for database operations related to the User entity.
 * It includes methods to insert a new user and authenticate a user during
 * login.
 */
public class UserDAO {

    private final Connection conn;

    /**
     * Constructs a UserDAO with the given database connection.
     *
     * @param conn the active JDBC connection to the database
     */
    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new user into the USER table.
     *
     * @param user the User object containing user information (name, email, hashed
     *             password, role)
     * @return true if the insertion is successful, false otherwise
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
     * Authenticates a user by checking the provided email and hashed password
     * against the database.
     *
     * @param email          the user's email
     * @param hashedPassword the user's hashed password
     * @return a User object if authentication is successful; null otherwise
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
                        rs.getString("USER_TYPE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
