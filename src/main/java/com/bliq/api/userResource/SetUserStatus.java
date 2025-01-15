package com.bliq.api.userResource;

import com.bliq.api.UserResponse;
import com.bliq.services.UserService;
import com.bliq.webSocket.Socket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Path("/set-user-status")
public class SetUserStatus {
    @POST
    @Produces("application/json")
    public UserResponse setStatus(@FormParam("userId") String user_id, @FormParam("status") String status) {
        try {
            if (status != null && !status.isEmpty()) {
                // Set the user's status
                // This is where you would call a service to update the user's status in the database
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
                EntityManager em = emf.createEntityManager();
                UserService userService = new UserService(em);

                // Call the service to update the user's status
                String[] updated = userService.updateUserStatus(user_id, status);

                if(updated[1].equals("error")){
                    return new UserResponse(updated[0], "error");
                }

                JsonObject message = Json.createObjectBuilder()
                        .add("type", "status")
                        .add("userId", user_id)
                        .add("status", status)
                        .build();

                Socket.broadcastToAll(message.toString());

                return new UserResponse("Status updated successfully", "success", status);
            }

            // If no status is provided, return an error response
            return new UserResponse("No status provided", "error");
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions and return an appropriate response
            return new UserResponse("Internal server error", "error");
        }
    }
}