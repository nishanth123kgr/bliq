package com.bliq.api.notificationResource;

import com.bliq.api.UserResponse;
import com.bliq.services.NotificationService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/create-notification")
public class CreateNotification {
    @POST
    @Produces("text/json")
    public UserResponse createNotification(
            @FormParam("req_msg_id") String req_msg_id,
            @FormParam("type") String type,
            @FormParam("user_id") String user_id
    ) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
            EntityManager em = emf.createEntityManager();

            NotificationService notificationService = new NotificationService(em);

            String[] result = notificationService.createNotification(req_msg_id, type, user_id);

            if (result[1].equals("error")) {
                return new UserResponse(result[0], "error");
            }

            return new UserResponse("Notification created successfully", "success");
        } catch (Exception e) {
            return new UserResponse("Error creating notification", "error");
        }

    }
}