package com.revature.foundations.servlets;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revature.foundations.daos.UserDAO;
import com.revature.foundations.dtos.requests.LoginRequest;
import com.revature.foundations.dtos.requests.ReimbursementSubmissionRequest;
import com.revature.foundations.dtos.requests.UpdateReimbursementRequest;
import com.revature.foundations.dtos.requests.UpdateUserRequest;
import com.revature.foundations.dtos.responses.AppUserResponse;
import com.revature.foundations.dtos.responses.Principal;
import com.revature.foundations.dtos.responses.ResourceCreationResponse;
import com.revature.foundations.models.AppUser;
import com.revature.foundations.models.Reimbursement;
import com.revature.foundations.models.ReimbursementStatus;
import com.revature.foundations.services.ReimbursementService;
import com.revature.foundations.services.TokenService;
import com.revature.foundations.services.UserService;
import com.revature.foundations.util.exceptions.AuthenticationException;
import com.revature.foundations.util.exceptions.InvalidRequestException;
import com.revature.foundations.util.exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ReimbursementServlet extends HttpServlet {

    private final TokenService tokenService;
    private final ReimbursementService reimbursementService;
    private final ObjectMapper mapper;

    public ReimbursementServlet(TokenService tokenService, ReimbursementService reimbursementService, ObjectMapper mapper) {
        this.tokenService = tokenService;
        this.reimbursementService = reimbursementService;
        this.mapper = mapper;
    }

    // Reimbursement submit endpoint
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();

        try {


            Principal potentiallyEmployee = tokenService.extractRequesterDetails(req.getHeader("Authorization"));
            if (!(potentiallyEmployee.getRole().equals("EMPLOYEE"))) {
                throw new InvalidRequestException("Only Employee's can submit Reimbursements");
            }

            ReimbursementSubmissionRequest aReimbursementSubmissionRequest = mapper.readValue(req.getInputStream(), ReimbursementSubmissionRequest.class);

            reimbursementService.logNewReimbursement(aReimbursementSubmissionRequest);
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            String payload = mapper.writeValueAsString(aReimbursementSubmissionRequest);
            resp.setContentType("application/json");
            writer.write(payload);


        } catch (InvalidRequestException | DatabindException e) {
            e.printStackTrace();
            resp.setStatus(400);
        } catch (AuthenticationException e) {
            resp.setStatus(401); // UNAUTHORIZED (no user found with provided credentials)
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }

    }


    //update user endpoint. Something only an admin can do.
    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter respWriter = resp.getWriter();

        try {

            Principal potentiallyAdmin = tokenService.extractRequesterDetails(req.getHeader("Authorization"));
            if(!(potentiallyAdmin.getRole().equals("FINANCE MANAGER"))){
                throw new InvalidRequestException("Bad Role");
            }

            UpdateReimbursementRequest anUpdateReimbursementRequest = mapper.readValue(req.getInputStream(), UpdateReimbursementRequest.class);
            System.out.println(anUpdateReimbursementRequest);
            Reimbursement updatedReimbursement = reimbursementService.updateReimbursementStatus(anUpdateReimbursementRequest);

            System.out.println(updatedReimbursement);

            resp.setStatus(201); // Succesful
            resp.setContentType("application/json");
            String payload = mapper.writeValueAsString(new ResourceCreationResponse(updatedReimbursement.getResolver_id()));
            respWriter.write(payload);

        } catch (InvalidRequestException | DatabindException e) {
            resp.setStatus(400); // BAD REQUEST
            e.printStackTrace();
        } catch (ResourceConflictException e) {
            e.printStackTrace();
            resp.setStatus(409); // CONFLICT
        } catch (Exception e) {
            e.printStackTrace(); // include for debugging purposes; ideally log it to a file
            resp.setStatus(500);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

       // String[] reqFrags = req.getRequestURI().split("/");
       // if (reqFrags.length == 4 && reqFrags[3].equals("availability")) {
       //     checkAvailability(req, resp);
       //     return; // necessary, otherwise we end up doing more work than was requested
        //}

        // TODO implement some security logic here to protect sensitive operations

        // get users (all, by id, by w/e)
//        HttpSession session = req.getSession(false);
//        if (session == null) {
//            resp.setStatus(401);
//            return;
//        }
//        Principal requester = (Principal) session.getAttribute("authUser");

        Principal requester = tokenService.extractRequesterDetails(req.getHeader("Authorization"));

        if (requester == null) {
            resp.setStatus(401);
            return;
        }
        if (!requester.getRole().equals("FINANCE MANAGER")) {
            resp.setStatus(403); // FORBIDDEN
            return;
        }

        List<Reimbursement> users = reimbursementService.getAllReimbursements();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String payload = mapper.writeValueAsString(users);
        resp.setContentType("application/json");
        resp.getWriter().write(payload);


    }

}
