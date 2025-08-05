/*
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : DTO for transferring user data across application layers, with Builder pattern added safely.
*/

package com.group.project.nabila.msiah.transferobject;

import java.io.Serializable;

/**
 * The {@code UserDTO} class is a Data Transfer Object that encapsulates user
 * data
 * including ID, name, email, password, and user type.
 * It provides constructors, getter/setter methods, and a Builder for clean
 * object construction.
 */
public class UserDTO implements Serializable {

    private int userId;
    private String name;
    private String email;
    private String password;
    private String userType;

    /**
     * Default constructor.
     */
    public UserDTO() {
    }

    /**
     * Full constructor with all fields.
     *
     * @param userId   the user's ID
     * @param name     the user's name
     * @param email    the user's email
     * @param password the user's password
     * @param userType the user's role (e.g., "manager", "operator")
     */
    public UserDTO(int userId, String name, String email, String password, String userType) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    /**
     * Constructor without userId, used for new users before persisting to database.
     *
     * @param name     the user's name
     * @param email    the user's email
     * @param password the user's password
     * @param userType the user's role
     */
    public UserDTO(String name, String email, String password, String userType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    /**
     * Gets the user ID.
     * 
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     * 
     * @param userId the user ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the user's name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     * 
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user's email.
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     * 
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's password.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     * 
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user type (role).
     * 
     * @return the userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Sets the user type (role).
     * 
     * @param userType the userType
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * Builder class for UserDTO to enable clean and flexible object creation.
     */
    public static class Builder {
        private int userId;
        private String name;
        private String email;
        private String password;
        private String userType;

        /**
         * Sets the user ID.
         * 
         * @param userId the user ID
         * @return the builder instance
         */
        public Builder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        /**
         * Sets the name.
         * 
         * @param name the name
         * @return the builder instance
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the email.
         * 
         * @param email the email
         * @return the builder instance
         */
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        /**
         * Sets the password.
         * 
         * @param password the password
         * @return the builder instance
         */
        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        /**
         * Sets the user type.
         * 
         * @param userType the user type (role)
         * @return the builder instance
         */
        public Builder setUserType(String userType) {
            this.userType = userType;
            return this;
        }

        /**
         * Builds and returns the final UserDTO instance.
         * 
         * @return a new {@code UserDTO} object
         */
        public UserDTO build() {
            return new UserDTO(userId, name, email, password, userType);
        }
    }

    /**
     * Returns a string representation of the UserDTO.
     * 
     * @return user details in string format
     */
    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
