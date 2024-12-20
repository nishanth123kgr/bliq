package com.bliq.services;

import com.bliq.models.ChatActions;
import com.bliq.models.Participants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class AdminActionService {
    private EntityManager em;

    public AdminActionService(EntityManager em) {
        this.em = em;
    }



    public String[] promoteAsAdmin(String user_id, String admin_id, String chat_id) {

        try{
            ParticipantService participantService = new ParticipantService(em);
            if(!participantService.isParticipant(chat_id, user_id)) {
                return new String[]{"User is not a participant", "error"};
            }
            if(participantService.isUserAdmin(chat_id, user_id)) {
                return new String[]{"User is already an admin", "error"};
            }
            if(!participantService.isUserAdmin(chat_id, admin_id)) {
                return new String[]{"User is not an admin", "error"};
            }
            Participants participant = participantService.getParticipant(chat_id, user_id);
            em.getTransaction().begin();
            participant.setAdmin(true);
            em.getTransaction().commit();

            ChatActionService chatActionService = new ChatActionService(em);
            chatActionService.createAction("1", chat_id, user_id, admin_id);

            return new String[]{"User promoted as admin", "success"};
        }
        catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Error promoting user as admin", "error"};
        }
    }

    public String[] demoteAsAdmin(String user_id, String admin_id, String chat_id) {

        try{
            ParticipantService participantService = new ParticipantService(em);
            if(!participantService.isParticipant(chat_id, user_id)) {
                return new String[]{"User is not a participant", "error"};
            }
            if(!participantService.isUserAdmin(chat_id, user_id)) {
                return new String[]{"User is not an admin", "error"};
            }
            if(!participantService.isUserAdmin(chat_id, admin_id)) {
                return new String[]{"User is not an admin", "error"};
            }
            Participants participant = participantService.getParticipant(chat_id, user_id);
            em.getTransaction().begin();
            participant.setAdmin(false);
            em.getTransaction().commit();

            ChatActionService chatActionService = new ChatActionService(em);
            chatActionService.createAction("2", chat_id, user_id, admin_id);

            return new String[]{"User demoted as participant", "success"};
        }
        catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Error demoting user as admin", "error"};
        }
    }

    public String[] removeParticipant(String user_id, String admin_id, String chat_id) {

        try{
            ParticipantService participantService = new ParticipantService(em);
            if(!participantService.isParticipant(chat_id, user_id)) {
                return new String[]{"User is not a participant", "error"};
            }
            if(!participantService.isUserAdmin(chat_id, admin_id)) {
                return new String[]{"User is not an admin", "error"};
            }
            Participants participant = participantService.getParticipant(chat_id, user_id);
            em.getTransaction().begin();
            em.remove(participant);
            em.getTransaction().commit();

            ChatActionService chatActionService = new ChatActionService(em);
            chatActionService.createAction("3", chat_id, user_id, admin_id);

            return new String[]{"User removed from chat", "success"};
        }
        catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Error removing user from chat", "error"};
        }
    }

    public String[] addParticipant(String chat_id, String user_id, String admin_id) {
        try {

            ParticipantService participantService = new ParticipantService(em);

            if(participantService.isParticipant(chat_id, user_id)) {
                return new String[]{"User is already a participant", "error"};
            }


            String[] participant = participantService.addParticipant(chat_id, user_id, admin_id, false);

            if(participant[1].equals("error")) {
                return participant;
            }
            // Return the chat_id and a success message
            return new String[]{"Participant added successfully", "success"};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Error adding participant", "error"};
        }
    }
}
