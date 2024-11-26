package com.bliq.api.participantResource;

import com.bliq.services.ParticipantService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
            return Response.ok(participantService.getParticipationsOfUser(user_id)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error")
                    .build();
        }
    }
}