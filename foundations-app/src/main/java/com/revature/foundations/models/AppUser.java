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

    public AppUser(String user_id, 
				   String username, 
				   String email, 
                   String password, 
				   String given_name,
				   String surname,
				   boolean is_active) {

        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.given_name = given_name;
		this.surname = surname;
		this.is_active = is_active; 
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
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

    public void set(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGivenName() {
        return given_name;
    }

    public void set(String given_name) {
        this.given_name = given_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UserRole getIsActive() {
        return is_active;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(user_id, appUser.user_id)
                && Objects.equals(username, appUser.username)
                && Objects.equals(email, appUser.email)
                && Objects.equals(password, appUser.password)
                && Objects.equals(given_name, appUser.given_name)
                && Objects.equals(surname, appUser.surname)
				&& Objects.equals(is_active, appUser.is_active)
                && Objects.equals(role, appUser.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, username, email, password, given_name, 
							surname, is_active, role);
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
				", is_active='" + is_active + '\'' +
                ", role=" + role +
                '}';
    }

}

