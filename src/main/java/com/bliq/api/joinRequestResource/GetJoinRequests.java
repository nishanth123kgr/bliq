package com.bliq.api.joinRequestResource;

import com.bliq.api.UserResponse;
import com.bliq.services.JoinRequestService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/get-join-requests")
public class GetJoinRequests {
    @GET
    @Produces("text/plain")
    public Response getRequests(
            @QueryParam("admin_id") String user_id,
            @QueryParam("group_id") String group_id
    ) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
            EntityManager em = emf.createEntityManager();

            JoinRequestService joinRequestService = new JoinRequestService(em);

            String[] result = joinRequestService.getJoinRequests(group_id, user_id);

            if (result[1].equals("error")) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(new UserResponse(result[0], "error"))
                        .build();
            }

            return Response.ok(result[0]).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new UserResponse(e.getMessage(), "error"))
                    .build();
        }
    }
}