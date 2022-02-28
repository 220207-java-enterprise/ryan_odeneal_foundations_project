package com.revature.foundations.dtos.responses;

import com.revature.foundations.models.AppUser;

public class AppUserResponse {

    private String user_id;
    private String given_name;
    private String surname;
    private String username;
    private String role;

    public AppUserResponse() {
        super();
    }

    public AppUserResponse(AppUser user) {
        this.user_id = user.getUser_id();
        this.given_name = user.getGiven_name();
        this.surname = user.getSurname();
        this.username = user.getUsername();
        this.role = user.getRole().getRoleName();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}