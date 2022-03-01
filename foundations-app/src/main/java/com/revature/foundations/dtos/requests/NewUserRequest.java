package com.revature.foundations.dtos.requests;
import com.revature.foundations.models.AppUser;
import com.revature.foundations.models.UserRole;

import java.util.UUID;

public class NewUserRequest {

    private String username;
    private String email;
    private String password;
    private String given_name;
    private String surname;
    private String role;

    public NewUserRequest() {
        super();
    }

    public NewUserRequest(String username, String email, String password, String given_name, String surname, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.given_name = given_name;
        this.surname = surname;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public AppUser extractUser() {
        String user_id = UUID.randomUUID().toString();
        String role_id = UUID.randomUUID().toString();
        UserRole aRole = new UserRole(role_id, this.role);
        return new AppUser(user_id, username, email, password, given_name, surname, false, aRole);
    }

    @Override
    public String toString() {
        return "NewUserRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", given_name='" + given_name + '\'' +
                ", surname='" + surname + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

