package com.bliq.api.joinRequestResource;

import com.bliq.services.JoinRequestService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/create-join-request")
public class CreateJoinRequest {
    @POST
    @Produces("text/json")
    public Response createRequest(
            @FormParam("user_id") String user_id,
            @FormParam("group_id") String group_id
    ) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
            EntityManager em = emf.createEntityManager();

            JoinRequestService joinRequestService = new JoinRequestService(em);

            String[] result = joinRequestService.createJoinRequest(user_id, group_id);

            if (result[1].equals("error")) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(result[0])
                        .build();
            }

            return Response.ok(result[0]).build();

        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error")
                    .build();
        }
    }
}