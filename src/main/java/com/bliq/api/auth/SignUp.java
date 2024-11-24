package com.bliq.api.auth;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import jakarta.persistence.*;

import com.bliq.models.Users;
import com.bliq.Utils;
import com.bliq.api.UserResponse;



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

            Users user = new Users();
            user.setMail(mail);
            user.setName(name);

            Utils utils = new Utils();
            user.setPaswdHash(utils.hashPassword(paswd));
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();

            // Return success message as json
            return new UserResponse("User created successfully", "success");
        } catch (Exception e) {
            return new UserResponse("User creation failed\n\n"+ e, "error");

        }

    }
}