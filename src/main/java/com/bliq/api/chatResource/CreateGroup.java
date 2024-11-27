package com.bliq.api.chatResource;

import com.bliq.api.UserResponse;
import com.bliq.services.ChatService;
import com.bliq.services.ParticipantService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;

import java.util.Arrays;
import java.util.Objects;

@Path("/create-group")
public class CreateGroup {
    @POST
    @Produces("application/json")
    public UserResponse createGroup(
            @FormParam("user_id") String user_id,
            @FormParam("group_name") String group_name,
            @FormParam("members") String group_members,
            @FormParam("is_private") boolean is_private) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
        EntityManager em = emf.createEntityManager();

        ChatService chatService = new ChatService(em);

        String[] group = chatService.createGroup(user_id, group_name, is_private);

        System.out.println(Arrays.toString(group));

        if (group[1].equals("error")) {
            return new UserResponse("Error creating group", "error");
        }

        String[] members = group_members.split(",");

        System.out.println(Arrays.toString(members));


        ParticipantService participantService = new ParticipantService(em);

        String[] participant_user = participantService.addParticipant(group[0], user_id, user_id, true);

        if (participant_user[1].equals("error")) {
            return new UserResponse(participant_user[0]+"Error Adding Admin User", "error");
        }

        for (String member : members) {
            String[] participant = participantService.addParticipant(group[0], member, user_id, Objects.equals(user_id, member));

            if (participant[1].equals("error")) {
                return new UserResponse("Error adding participant "+member, "error");
            }
        }





        return new UserResponse("Group created successfully", "success", group[0]);
    }
}