package com.bliq.api.notificationResource;


import com.bliq.api.UserResponse;
import com.bliq.services.NotificationService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/get-notifications")
public class GetNotificationsForUser {
    @GET
    @Produces("text/json")
    public Response getNotifications(
            @QueryParam("user_id") String user_id
    ) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
            EntityManager em = emf.createEntityManager();

            NotificationService notificationService = new NotificationService(em);

            String[] notifications = notificationService.getNotifications(user_id);

            if (notifications[1].equals("error")) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(notifications[0])
                        .build();
            }

            return Response.ok(notifications[0]).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error")
                    .build();
        }
    }
}

