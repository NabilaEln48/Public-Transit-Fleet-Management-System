/*
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : Implements the UserDAO interface to handle user login and registration operations using JDBC.
*/

package DataAccessLayer;

import Transferobject.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    private final Connection conn;

    public UserDAOImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new user into the user table.
     *
     * @param user UserDTO object containing user details
     * @return true if insertion is successful; false otherwise
     */
    @Override
    public boolean insertUser(UserDTO user) {
        String sql = "INSERT INTO user (name, email, password, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getUserType());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in insertUser (UserDAOImpl):");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Authenticates a user by email and hashed password.
     *
     * @param email          the user's email
     * @param hashedPassword the hashed password
     * @return UserDTO if credentials match; null otherwise
     */
    @Override
    public UserDTO authenticate(String email, String hashedPassword) {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, hashedPassword);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new UserDTO(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("role"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in authenticate (UserDAOImpl):");
            e.printStackTrace();
        }
        return null;
    }
}