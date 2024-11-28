package com.bliq.api.joinRequestResource;

import com.bliq.api.UserResponse;
import com.bliq.services.JoinRequestService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/reject-request")
public class RejectRequest {
    @POST
    @Produces("text/json")
    public Response acceptRequest(
            @FormParam("req_id") String req_id,
            @FormParam("admin_id") String admin_id
    ) {
        try {
            System.out.println("Accepting request");


            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
            EntityManager em = emf.createEntityManager();

            JoinRequestService joinRequestService = new JoinRequestService(em);

            String[] result = joinRequestService.rejectJoinRequest(req_id, admin_id);

            if (result[1].equals("error")) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(new UserResponse(result[0], "error"))
                        .build();
            }

            return Response.ok(new UserResponse(result[0], "success")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new UserResponse(e.getMessage(), "error"))
                    .build();
        }

    }
}