package com.bliq.api;

import com.bliq.models.Users;
import com.bliq.services.SessionService;
import com.bliq.services.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/search-users")
public class SearchUser {
    @GET
    @Produces("application/json")
    public Response search(@QueryParam("query") String query) {
        try {
            if (query != null && !query.isEmpty()) {
                // Create an EntityManagerFactory and EntityManager
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
                EntityManager em = emf.createEntityManager();
                UserService userService = new UserService(em);

                // Call searchUsers and return the result
                List users = userService.searchUsers(query);
                return Response.ok(users).build();
            }

            // If no query is provided, return an error response
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new UserResponse("No query provided", "error"))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions and return an appropriate response
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new UserResponse("Internal server error", "error"))
                    .build();
        }
    }
}