package com.revature.foundations.services;


import com.revature.foundations.dtos.requests.LoginRequest;
import com.revature.foundations.dtos.requests.NewUserRequest;
import com.revature.foundations.dtos.requests.UpdateUserRequest;
import com.revature.foundations.dtos.responses.AppUserResponse;
import com.revature.foundations.models.AppUser;
import com.revature.foundations.daos.UserDAO;
import com.revature.foundations.models.UserRole;
import com.revature.foundations.util.exceptions.AuthenticationException;
import com.revature.foundations.util.exceptions.InvalidRequestException;
import com.revature.foundations.util.exceptions.ResourceConflictException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserService {

    private UserDAO userDAO; // a dependency of UserService

    // Constructor injection
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<AppUserResponse> getAllUsers() {

        // Pre-Java 8 mapping logic (without Streams)
//        List<AppUser> users = userDAO.getAll();
//        List<AppUserResponse> userResponses = new ArrayList<>();
//        for (AppUser user : users) {
//            userResponses.add(new AppUserResponse(user));
//        }
//        return userResponses;

        // Java 8+ mapping logic (with Streams)
        return userDAO.getAll()
                .stream()
                .map(AppUserResponse::new) // intermediate operation
                .collect(Collectors.toList()); // terminal operation
    }

    public AppUser register(NewUserRequest newUserRequest) throws IOException {

        AppUser newUser = newUserRequest.extractUser();

        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Bad registration details given.");
        }

        boolean usernameAvailable = isUsernameAvailable(newUser.getUsername());
        boolean emailAvailable = isEmailAvailable(newUser.getEmail());

        if (!usernameAvailable || !emailAvailable) {
            String msg = "The values provided for the following fields are already taken by other users: ";
            if (!usernameAvailable) msg += "username ";
            if (!emailAvailable) msg += "email";
            throw new ResourceConflictException(msg);
        }

        // TODO encrypt provided password before storing in the database

        // this is a holdover from quizzard. Don't think I need it. I implemented it better in newuserequest I think.
        // newUser.setId(UUID.randomUUID().toString());
        // newUser.setRole(new UserRole("7c3521f5-ff75-4e8a-9913-01d15ee4dc97", "BASIC_USER")); // All newly registered users start as BASIC_USER
        userDAO.save(newUser);

        return newUser;
    }

    public AppUser login(LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (!isUsernameValid(username) || !isPasswordValid(password)) {
            throw new InvalidRequestException("Invalid credentials provided!");
        }

        // TODO encrypt provided password (assumes password encryption is in place) to see if it matches what is in the DB

        AppUser authUser = userDAO.findUserByUsernameAndPassword(username, password);

        if (authUser == null) {
            throw new AuthenticationException();
        }

        if(authUser.isIs_active() == false) {
            throw new AuthenticationException("This user is not active");
        }

        return authUser;

    }

    //redundant?
    public AppUser updatedUser(UpdateUserRequest updateRequest) {
        AppUser updatedUser = updateRequest.extractUser();
        userDAO.update(updatedUser);
        return updatedUser;
    }

    private boolean isUserValid(AppUser appUser) {

        // First name and last name are not just empty strings or filled with whitespace
        if (appUser.getGiven_name().trim().equals("") || appUser.getSurname().trim().equals("")) {
            return false;
        }

        // Usernames must be a minimum of 8 and a max of 25 characters in length, and only contain alphanumeric characters.
        if (!isUsernameValid(appUser.getUsername())) {
            return false;
        }

        // Passwords require a minimum eight characters, at least one uppercase letter, one lowercase
        // letter, one number and one special character
        if (!isPasswordValid(appUser.getPassword())) {
            return false;
        }

        ArrayList<String> validRoles = new ArrayList<String>();
        validRoles.add("FINANCE MANAGER");
        validRoles.add("ADMIN");
        validRoles.add("EMPLOYEE");
        if(!(validRoles.contains(appUser.getRole().getRoleName()))) {
            return false;
        }

        // Basic email validation
        return isEmailValid(appUser.getEmail());

    }

    public boolean isEmailValid(String email) {
        if (email == null) return false;
        return email.matches("^[^@\\s]+@[^@\\s.]+\\.[^@.\\s]+$");
    }

    public boolean isUsernameValid(String username) {
        if (username == null) return false;
        return username.matches("^[a-zA-Z0-9]{8,25}");
    }

    public boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }

    public boolean isUsernameAvailable(String username) {
        return userDAO.findUserByUsername(username) == null;
    }

    public boolean isEmailAvailable(String email) {
        return userDAO.findUserByEmail(email) == null;
    }

}