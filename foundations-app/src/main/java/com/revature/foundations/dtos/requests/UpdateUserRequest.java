package com.revature.foundations.dtos.requests;
import com.revature.foundations.daos.UserDAO;
import com.revature.foundations.models.AppUser;
import com.revature.foundations.models.UserRole;

import java.util.UUID;

public class UpdateUserRequest {

    private String user_id;
    private String username;
    private String email;
    private String password;
    private String given_name;
    private String surname;
    private boolean is_active;
    private String role;

    public UpdateUserRequest() {
        super();
    }

    public UpdateUserRequest(String user_id, String username, String email, String password, String given_name, String surname, boolean is_active, String role) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.given_name = given_name;
        this.surname = surname;
        this.is_active = is_active;
        this.role = role;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public AppUser extractUser() {
        UserDAO daoToPullUserForRole_Id = new UserDAO();
        AppUser pulledUser = daoToPullUserForRole_Id.getById(this.user_id);
        UserRole aRole = new UserRole(pulledUser.getRole().getId(), role);
        return new AppUser(this.user_id, this.username, this.email, this.password, this.given_name, this.surname, this.is_active, aRole);
    }

    @Override
    public String toString() {
        return "UpdateUserRequest{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", given_name='" + given_name + '\'' +
                ", surname='" + surname + '\'' +
                ", is_active=" + is_active +
                ", role='" + role + '\'' +
                '}';
    }
}

