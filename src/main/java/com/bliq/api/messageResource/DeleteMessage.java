package com.bliq.api.messageResource;

import com.bliq.api.UserResponse;
import com.bliq.services.MessageService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;

@Path("/delete-message")
public class DeleteMessage {
    @POST
    @Produces("text/json")
    public UserResponse deleteMessage(
            @FormParam("message_id") String message_id
    ) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
            EntityManager em = emf.createEntityManager();
            MessageService messageService = new MessageService(em);

            String[] result = messageService.deleteMessage(message_id);

            if (result[1].equals("error")) {
                return new UserResponse(result[0], "error");
            }

            return new UserResponse("Message deleted successfully", "success");

        } catch (Exception e) {
            return new UserResponse("Error deleting message", "error");
        }

    }
}
