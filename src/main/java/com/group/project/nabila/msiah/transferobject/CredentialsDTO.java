/*
    Author       : Nabila Msiah 041146732
    Course       : CST8288 - Group Project - Authentication Module (Nabila Msiah Part)
    Description  : DTO for holding database authentication credentials.
*/

package com.group.project.nabila.msiah.transferobject;

/**
 * A Data Transfer Object used to encapsulate credentials
 * required for authenticating or accessing secure resources (e.g., database).
 */
public class CredentialsDTO {
    private String username;
    private String password;

    public CredentialsDTO() {
    }

    public CredentialsDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
