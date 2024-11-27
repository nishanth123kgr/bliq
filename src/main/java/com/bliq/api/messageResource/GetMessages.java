package com.bliq.api.messageResource;

import com.bliq.services.MessageService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/get-messages")
public class GetMessages {
    @GET
    @Produces("text/json")
    public Response getMessages(
            @QueryParam("chat_id") String chat_id
    ) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
            EntityManager em = emf.createEntityManager();

            MessageService messageService = new MessageService(em);

            String[] messages = messageService.getMessages(chat_id);

            if (messages[1].equals("error")) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(messages[0])
                        .build();
            }

            return Response.ok(messages[0]).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error")
                    .build();
        }
    }
}