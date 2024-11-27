package com.bliq.api.joinRequestResource;

import com.bliq.services.JoinRequestService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/accept-request")
public class AcceptRequest {
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

            String[] result = joinRequestService.acceptJoinRequest(req_id, admin_id);

            if (result[1].equals("error")) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(result[0])
                        .build();
            }

            return Response.ok(result[0]).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error")
                    .build();
        }

    }
}