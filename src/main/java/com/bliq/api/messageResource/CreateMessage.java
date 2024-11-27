package com.bliq.api.messageResource;

import com.bliq.api.UserResponse;
import com.bliq.services.MessageService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;

@Path("/create-message")
public class CreateMessage {
    @POST
    @Produces("text/json")
    public UserResponse hello(
            @FormParam("chat_id") String chat_id,
            @FormParam("user_id") String user_id,
            @FormParam("message") String message
    ) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
            EntityManager em = emf.createEntityManager();
            MessageService messageService = new MessageService(em);

            String[] result = messageService.createMessage(chat_id, user_id, message);

            if (result[1].equals("error")) {
                return new UserResponse(result[0], "error");
            }

            return new UserResponse("Message sent successfully", "success");

        } catch (Exception e) {
            return new UserResponse("Error sending message", "error");
        }

    }
}