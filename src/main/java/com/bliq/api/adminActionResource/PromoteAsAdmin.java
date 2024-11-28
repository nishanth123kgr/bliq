package com.bliq.api.adminActionResource;

import com.bliq.api.UserResponse;
import com.bliq.services.AdminActionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/promote-as-admin")
public class PromoteAsAdmin {
    @POST
    @Produces("text/json")
    public Response promoteAsAdmin(
            @FormParam("user_id") String user_id,
            @FormParam("admin_id") String admin_id,
            @FormParam("chat_id") String chat_id
    ) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
            EntityManager em = emf.createEntityManager();

            System.out.println("user_id: " + user_id);
            System.out.println("admin_id: " + admin_id);
            System.out.println("chat_id: " + chat_id);

            AdminActionService adminActionService = new AdminActionService(em);

            String[] result = adminActionService.promoteAsAdmin(user_id, admin_id, chat_id);

            if (result[1].equals("error")) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(new UserResponse(result[0], "error"))
                        .build();
            }

            return Response.ok(new UserResponse(result[0], "success")).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new UserResponse("Internal server error", "error"))
                    .build();

        }
    }
}
