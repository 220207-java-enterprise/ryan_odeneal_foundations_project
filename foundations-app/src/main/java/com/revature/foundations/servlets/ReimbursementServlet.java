package com.revature.foundations.servlets;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revature.foundations.daos.UserDAO;
import com.revature.foundations.dtos.requests.LoginRequest;
import com.revature.foundations.dtos.requests.ReimbursementSubmissionRequest;
import com.revature.foundations.dtos.responses.Principal;
import com.revature.foundations.models.AppUser;
import com.revature.foundations.services.ReimbursementService;
import com.revature.foundations.services.TokenService;
import com.revature.foundations.services.UserService;
import com.revature.foundations.util.exceptions.AuthenticationException;
import com.revature.foundations.util.exceptions.InvalidRequestException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
            System.out.println(potentiallyEmployee);
            if (!(potentiallyEmployee.getRole().equals("Employee"))) {
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
}
