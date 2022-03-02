package com.revature.foundations.services;

import com.revature.foundations.daos.ReimbursementDAO;
import com.revature.foundations.dtos.requests.LoginRequest;
import com.revature.foundations.dtos.requests.NewUserRequest;
import com.revature.foundations.dtos.requests.ReimbursementSubmissionRequest;
import com.revature.foundations.dtos.requests.UpdateUserRequest;
import com.revature.foundations.dtos.responses.AppUserResponse;
import com.revature.foundations.models.AppUser;
import com.revature.foundations.daos.UserDAO;
import com.revature.foundations.models.Reimbursement;
import com.revature.foundations.models.UserRole;
import com.revature.foundations.util.exceptions.AuthenticationException;
import com.revature.foundations.util.exceptions.InvalidRequestException;
import com.revature.foundations.util.exceptions.ResourceConflictException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReimbursementService {
    private ReimbursementDAO aReimbursementDAO;

    // Constructor injection
    public ReimbursementService(ReimbursementDAO reimbursementDAO) {
        this.aReimbursementDAO = reimbursementDAO;
    }

    public Reimbursement logNewReimbursement(ReimbursementSubmissionRequest aReimbursementSubmissionRequest) throws IOException {


        Reimbursement newReimbursement = aReimbursementSubmissionRequest.extractReimbursement();

    //    if (!isUserValid(newUser)) {
    //        throw new InvalidRequestException("Bad registration details given.");
    //    }

    //    boolean usernameAvailable = isUsernameAvailable(newUser.getUsername());
    //    boolean emailAvailable = isEmailAvailable(newUser.getEmail());

    //    if (!usernameAvailable || !emailAvailable) {
    //        String msg = "The values provided for the following fields are already taken by other users: ";
    //        if (!usernameAvailable) msg += "username ";
    //        if (!emailAvailable) msg += "email";
    //        throw new ResourceConflictException(msg);
    //    }

        // TODO encrypt provided password before storing in the database

        // this is a holdover from quizzard. Don't think I need it. I implemented it better in newuserequest I think.
        // newUser.setId(UUID.randomUUID().toString());
        // newUser.setRole(new UserRole("7c3521f5-ff75-4e8a-9913-01d15ee4dc97", "BASIC_USER")); // All newly registered users start as BASIC_USER
        aReimbursementDAO.save(newReimbursement);

        return newReimbursement;
    }

}
