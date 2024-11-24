package com.bliq.api.auth;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import jakarta.persistence.*;

import com.bliq.Utils;
import com.bliq.api.UserResponse;
import com.bliq.services.UserService;



@Path("/signup")
public class SignUp {
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("text/json")
    public UserResponse signup(
         @FormParam("name") String name,
         @FormParam("email") String mail,
         @FormParam("password") String paswd
    ) {
        try{
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
            EntityManager em = emf.createEntityManager();

            /*Users user = new Users();
            user.setMail(mail);
            user.setName(name);

            Utils utils = new Utils();
            user.setPaswdHash(utils.hashPassword(paswd));
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();*/

            UserService userService = new UserService(em);
            String[] resp = userService.createUser(name, mail, new Utils().hashPassword(paswd));
            if(resp[1].equals("error")){
                return new UserResponse(resp[0], "error");
            }
            // Return success message as json
            return new UserResponse("User created successfully", "success");
        } catch (Exception e) {
            return new UserResponse("User creation failed\n\n"+ e, "error");

        }

    }
}