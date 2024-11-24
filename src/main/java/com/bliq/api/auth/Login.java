package com.bliq.api.auth;

import com.bliq.services.UserService;
import com.bliq.services.SessionService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import jakarta.persistence.*;

import com.bliq.models.*;
import com.bliq.Utils;
import com.bliq.api.UserResponse;

import java.util.Objects;


@Path("/login")
public class Login {
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("text/json")
    public UserResponse signup(
            @FormParam("email") String mail,
            @FormParam("password") String paswd,
            @FormParam("device") String device
    ) {
        try{
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
            EntityManager em = emf.createEntityManager();
            UserService userService = new UserService(em);
            Utils utils = new Utils();

            String[] user_details = userService.checkUserExists(mail);

            // Check if user exists
            if (Objects.equals(user_details[1], "false")) {
                return new UserResponse("User does not exist", "error");
            }

            // Check if password is correct
            if (!utils.checkPassword(paswd, user_details[0])) {
                return new UserResponse("Incorrect password", "error");
            }

            // Create session
            SessionService sessionService = new SessionService(em);
            int user_id = Integer.parseInt(user_details[2]);
            String sessionToken = utils.generateSessionToken();
            String[] session_details = sessionService.createSession(user_id, sessionToken, device, true);


            if (Objects.equals(session_details[1], "error")) {
                return new UserResponse(session_details[0], "error");
            }



            // Return success message as json
            return new UserResponse("Login Success", "success");
        } catch (Exception e) {
            return new UserResponse("Login failed\n\n"+ e, "error");

        }

    }
}