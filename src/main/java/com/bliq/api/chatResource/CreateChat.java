package com.bliq.api.chatResource;

import com.bliq.api.UserResponse;
import com.bliq.services.ChatService;
import com.bliq.services.ParticipantService;
import com.bliq.services.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;

@Path("/create-chat")
public class CreateChat {
    @POST
    @Produces("application/json")
    public UserResponse setStatus(@FormParam("user_id") String user_id, @FormParam("receiver_id") String receiver_id) {
        try {
            if (user_id != null && !user_id.isEmpty() && receiver_id != null && !receiver_id.isEmpty()) {
                // Create an EntityManagerFactory and EntityManager
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
                EntityManager em = emf.createEntityManager();
                ChatService chatService = new ChatService(em);

                // Call createChat and return the result
                String[] chat = chatService.createChat(user_id, receiver_id);

                if (chat[1].equals("error")) {
                    return new UserResponse(chat[0], "error");
                }

                ParticipantService participantService = new ParticipantService(em);

                // Call addParticipant and return the result
                String[] participant_user = participantService.addParticipant(chat[0], user_id, user_id);

                if (participant_user[1].equals("error")) {
                    return new UserResponse(participant_user[0], "error");
                }

                String[] participant_receiver = participantService.addParticipant(chat[0], receiver_id, user_id);

                if (participant_receiver[1].equals("error")) {
                    return new UserResponse(participant_receiver[0], "error");
                }


                return new UserResponse("Chat created successfully", "success", new String[]{chat[0], participant_user[0], participant_receiver[0]});
            }

            // If no user_id or receiver_id is provided, return an error response
            return new UserResponse("No user_id or receiver_id provided", "error");
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions and return an appropriate response
            return new UserResponse("Internal server error", "error");
        }
    }
}