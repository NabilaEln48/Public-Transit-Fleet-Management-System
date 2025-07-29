/*
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : DTO (Data Transfer Object) class for the User entity.
                   This class encapsulates user-related data such as id, name, email,
                   hashed password, and role. It is used to transfer data between layers.
*/

package com.group.project.nabila.msiah.DTOLayer;

/**
 * Represents a User entity with essential authentication and role information.
 */
public class User {
    private int id;
    private String name;
    private String email;
    private String password; // stored hashed
    private String role;

    /**
     * Default constructor.
     */
    public User() {}

    /**
     * Constructor with all fields.
     *
     * @param id       Unique identifier for the user.
     * @param name     Full name of the user.
     * @param email    Email address used for login.
     * @param password Hashed password.
     * @param role     User role (e.g., admin, manager, staff).
     */
    public User(int id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Constructor without ID (used before insertion to DB).
     *
     * @param name     Full name of the user.
     * @param email    Email address used for login.
     * @param password Hashed password.
     * @param role     User role (e.g., admin, manager, staff).
     */
    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /** @return User ID */
    public int getId() { return id; }

    /** @param id Set user ID */
    public void setId(int id) { this.id = id; }

    /** @return User full name */
    public String getName() { return name; }

    /** @param name Set user full name */
    public void setName(String name) { this.name = name; }

    /** @return User email address */
    public String getEmail() { return email; }

    /** @param email Set user email address */
    public void setEmail(String email) { this.email = email; }

    /** @return Hashed password */
    public String getPassword() { return password; }

    /** @param password Set hashed password */
    public void setPassword(String password) { this.password = password; }

    /** @return User role */
    public String getRole() { return role; }

    /** @param role Set user role */
    public void setRole(String role) { this.role = role; }
}
