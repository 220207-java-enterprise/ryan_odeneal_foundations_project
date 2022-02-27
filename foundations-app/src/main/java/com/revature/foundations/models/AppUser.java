package com.revature.foundations.models;

import java.util.Objects;

// POJO = Plain Ol' Java Object
// Contains NO BUSINESS LOGIC
// Simple encapsulation of some domain object's states
public class AppUser {

	private String user_id;
	private String username;
	private String email;
	private String password;
	private String given_name;
	private String surname;
	private boolean is_active;
	private UserRole role;

    public AppUser() {
        super();
    }

    public AppUser(String user_id, String username, String email, String password, String given_name, String surname, boolean is_active) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.given_name = given_name;
        this.surname = surname;
        this.is_active = is_active;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppUser)) return false;
        AppUser appUser = (AppUser) o;
        return isIs_active() == appUser.isIs_active() && Objects.equals(getUser_id(), appUser.getUser_id()) && Objects.equals(getUsername(), appUser.getUsername()) && Objects.equals(getEmail(), appUser.getEmail()) && Objects.equals(getPassword(), appUser.getPassword()) && Objects.equals(getGiven_name(), appUser.getGiven_name()) && Objects.equals(getSurname(), appUser.getSurname()) && Objects.equals(getRole(), appUser.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser_id(), getUsername(), getEmail(), getPassword(), getGiven_name(), getSurname(), isIs_active(), getRole());
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", given_name='" + given_name + '\'' +
                ", surname='" + surname + '\'' +
                ", is_active=" + is_active +
                ", role=" + role +
                '}';
    }
}

