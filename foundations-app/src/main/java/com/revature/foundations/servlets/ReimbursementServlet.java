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
import java.util.ArrayList;
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


    //update reimbursement endpoint. Something only a finance manager can do.
    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter respWriter = resp.getWriter();

        try {

            Principal potentiallyAdmin = tokenService.extractRequesterDetails(req.getHeader("Authorization"));
            if(!(potentiallyAdmin.getRole().equals("FINANCE MANAGER"))){
                throw new InvalidRequestException("Bad Role");
            }

            UpdateReimbursementRequest anUpdateReimbursementRequest = mapper.readValue(req.getInputStream(), UpdateReimbursementRequest.class);

            Reimbursement updatedReimbursement = reimbursementService.updateReimbursementStatus(anUpdateReimbursementRequest);



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
        if (!(requester.getRole().equals("FINANCE MANAGER") || requester.getRole().equals("EMPLOYEE"))) {
            resp.setStatus(403); // FORBIDDEN
            return;
        }

        List<Reimbursement> users = reimbursementService.getAllReimbursements();
        switch (requester.getRole()) {
            case "FINANCE MANAGER":
                if(req.getHeader("Status-Filter").equals("PENDING")) {
                    List<Reimbursement> pending_users = new ArrayList<>();
                    for(Reimbursement aReimbursement : users) {
                        if(aReimbursement.getStatus().getStatus_id().equals("PENDING")) {
                            pending_users.add(aReimbursement);

                        }
                    }
                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    String payload = mapper.writeValueAsString(pending_users);
                    resp.setContentType("application/json");
                    resp.getWriter().write(payload);
                } else if(req.getHeader("Status-Filter").equals("APPROVED")) {
                    List<Reimbursement> approved_users = new ArrayList<>();
                    for(Reimbursement aReimbursement : users) {
                        if (aReimbursement.getStatus().getStatus_id().equals("APPROVED")) {
                            approved_users.add(aReimbursement);

                        }
                    }
                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    String payload = mapper.writeValueAsString(approved_users);
                    resp.setContentType("application/json");
                    resp.getWriter().write(payload);
                }  else if (req.getHeader("Status-Filter").equals("DECLINED")) {
                            List<Reimbursement> declined_users = new ArrayList<>();
                            for(Reimbursement aReimbursement : users) {
                                if (aReimbursement.getStatus().getStatus_id().equals("DECLINED")) {
                                    declined_users.add(aReimbursement);
                                }
                            }
                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    String payload = mapper.writeValueAsString(declined_users);
                    resp.setContentType("application/json");
                    resp.getWriter().write(payload);
                        } else {

                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    String payload = mapper.writeValueAsString(users);
                    resp.setContentType("application/json");
                    resp.getWriter().write(payload);
                }
            case "EMPLOYEE":
                List<Reimbursement> employee_reimbursements = new ArrayList<>();
                for(Reimbursement aReimbursement : users) {
                    if(aReimbursement.getAuthor_id().equals(tokenService.extractRequesterDetails(req.getHeader("Authorization")).getId())) {
                        employee_reimbursements.add(aReimbursement);
                    }
                }
                if(req.getHeader("Status-Filter").equals("PENDING")) {
                    List<Reimbursement> pending_users = new ArrayList<>();
                    for(Reimbursement aReimbursement : employee_reimbursements) {
                        if(aReimbursement.getStatus().getStatus_id().equals("PENDING")) {
                            pending_users.add(aReimbursement);
                        }
                    }
                    System.out.println(pending_users);
                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    String payload = mapper.writeValueAsString(pending_users);
                    resp.setContentType("application/json");
                    resp.getWriter().write(payload);
                } else if(req.getHeader("Status-Filter").equals("APPROVED")) {
                    List<Reimbursement> approved_users = new ArrayList<>();
                    for(Reimbursement aReimbursement : employee_reimbursements) {
                        if (aReimbursement.getStatus().getStatus_id().equals("APPROVED")) {
                            approved_users.add(aReimbursement);
                        }

                    }
                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    String payload = mapper.writeValueAsString(approved_users);
                    resp.setContentType("application/json");
                    resp.getWriter().write(payload);
                }  else if (req.getHeader("Status-Filter").equals("DECLINED")) {
                    List<Reimbursement> declined_users = new ArrayList<>();
                    for(Reimbursement aReimbursement : employee_reimbursements) {
                        if (aReimbursement.getStatus().getStatus_id().equals("DECLINED")) {
                            declined_users.add(aReimbursement);

                        }

                    }
                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    String payload = mapper.writeValueAsString(declined_users);
                    resp.setContentType("application/json");
                    resp.getWriter().write(payload);
                } else {

                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    String payload = mapper.writeValueAsString(employee_reimbursements);
                    resp.setContentType("application/json");
                    resp.getWriter().write(payload);
                }
        }

    }

}
