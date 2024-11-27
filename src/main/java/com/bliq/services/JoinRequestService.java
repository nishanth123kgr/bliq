package com.bliq.services;

import com.bliq.models.JoinRequests;
import com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class JoinRequestService {
    private EntityManager em;

    public JoinRequestService(EntityManager em) {
        this.em = em;
    }

    public String[] createJoinRequest(String user_id, String chat_id) {
        try {
            EntityManagerFactory emf = jakarta.persistence.Persistence.createEntityManagerFactory("bliq");
            EntityManager em = emf.createEntityManager();

            ChatService chatService = new ChatService(em);

            if(!chatService.chatExists(chat_id)) {
                return new String[]{"Chat does not exist", "error"};
            }
            if(chatService.isPrivateChat(chat_id)) {
                return new String[]{"Chat is private", "error"};
            }

            UserService userService = new UserService(em);
            if(!userService.userExists(user_id)) {
                return new String[]{"User does not exist", "error"};
            }

            ParticipantService participantService = new ParticipantService(em);
            if(participantService.isParticipant(chat_id, user_id)) {
                return new String[]{"User is already a participant", "error"};
            }

            JoinRequests joinRequest = new JoinRequests(user_id, chat_id);
            em.getTransaction().begin();
            em.persist(joinRequest);
            em.getTransaction().commit();
            return new String[]{"Join request created successfully", "success", String.valueOf(joinRequest.getId())};

        }
        catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Error creating join request", "error"};
        }
    }

    public String[] acceptJoinRequest(String join_request_id, String admin_id) {
        try {

            JoinRequests joinRequest = em.find(JoinRequests.class, Long.parseLong(join_request_id));
            if(joinRequest == null) {
                return new String[]{"Join request not found", "error"};
            }

            ParticipantService participantService = new ParticipantService(em);

            if(!participantService.isUserAdmin(String.valueOf(joinRequest.getChatId()), admin_id)) {
                return new String[]{"User is not an admin", "error"};
            }

            String chatId = String.valueOf(joinRequest.getChatId());
            String userId = String.valueOf(joinRequest.getUserId());


            participantService.addParticipant(chatId, userId, admin_id, false);
            em.getTransaction().begin();
            em.remove(joinRequest);
            em.getTransaction().commit();
            return new String[]{"Join request accepted successfully", "success"};
        }
        catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Error accepting join request", "error"};
        }
    }

    public String[] rejectJoinRequest(String join_request_id, String admin_id) {
        try {
            JoinRequests joinRequest = em.find(JoinRequests.class, Long.parseLong(join_request_id));
            if(joinRequest == null) {
                return new String[]{"Join request not found", "error"};
            }

            ParticipantService participantService = new ParticipantService(em);

            if(!participantService.isUserAdmin(String.valueOf(joinRequest.getChatId()), admin_id)) {
                return new String[]{"User is not an admin", "error"};
            }

            em.getTransaction().begin();
            em.remove(joinRequest);
            em.getTransaction().commit();
            return new String[]{"Join request rejected successfully", "success"};
        }
        catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Error rejecting join request", "error"};
        }
    }

    public String[] getJoinRequests(String chat_id, String admin_id) {
        try {
            ParticipantService participantService = new ParticipantService(em);
            if(!participantService.isUserAdmin(chat_id, admin_id)) {
                return new String[]{"User is not an admin", "error"};
            }
            List<JoinRequests> joinRequests = em.createQuery("SELECT j FROM JoinRequests j WHERE j.chatId = :chatId", JoinRequests.class)
                    .setParameter("chatId", Long.parseLong(chat_id))
                    .getResultList();
            return new String[]{new Gson().toJson(joinRequests), "success"};
        }
        catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Error getting join requests", "error"};
        }
    }




}
