package com.bliq.api.participantResource;

import com.bliq.api.UserResponse;
import com.bliq.services.ChatService;
import com.bliq.services.ParticipantService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/get-groups")
public class GetGroupsOfUser {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getParticipations(@QueryParam("user_id") String user_id) {
        try {
            // Create an EntityManagerFactory and EntityManager
            System.out.println("user_id: " + user_id);

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
            EntityManager em = emf.createEntityManager();
            ChatService chatService = new ChatService(em);

            List groups = chatService.getGroupsAUserPartOf(user_id);


            return Response.ok(groups).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new UserResponse("Internal server error", "error"))
                    .build();
        }
    }
}