package com.bliq.api.messageResource;

import com.bliq.api.UserResponse;
import com.bliq.models.ReadList;
import com.bliq.services.MessageService;
import com.bliq.services.ReadListService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/get-views")
public class GetViewsOfMessage {
    @GET
    @Produces("text/json")
    public Response getViews(
            @QueryParam("message_id") String message_id
    ) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
            EntityManager em = emf.createEntityManager();

            MessageService messageService = new MessageService(em);

            if(!messageService.messageExists(message_id)){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(new UserResponse("Message does not exist", "error"))
                        .build();
            }

            ReadListService readListService = new ReadListService(em);

            List<ReadList> views = readListService.getReadList(message_id);

            if (views == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(new UserResponse("No Message Views", "error"))
                        .build();
            }

            return Response.ok(views).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new UserResponse(e.getMessage(), "error"))
                    .build();
        }

    }
}