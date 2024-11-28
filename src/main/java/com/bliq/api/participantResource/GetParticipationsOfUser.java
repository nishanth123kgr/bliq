package com.bliq.api.participantResource;

import com.bliq.api.UserResponse;
import com.bliq.services.ParticipantService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/get-participations")
public class GetParticipationsOfUser {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getParticipations(@QueryParam("user_id") String user_id) {
        try {
            // Create an EntityManagerFactory and EntityManager
            System.out.println("user_id: " + user_id);

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
            EntityManager em = emf.createEntityManager();
            ParticipantService participantService = new ParticipantService(em);

            List participations = participantService.getParticipationsOfUser(user_id);


            return Response.ok(participations).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new UserResponse(e.getMessage(), "error"))
                    .build();
        }
    }
}