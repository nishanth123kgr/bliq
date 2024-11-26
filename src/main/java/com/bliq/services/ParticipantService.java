package com.bliq.services;

import com.bliq.models.Participants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ParticipantService {
    private EntityManager em;

    public ParticipantService(EntityManager em) {
        this.em = em;
    }

    public String[] addParticipant(String chat_id, String user_id, String added_by) {
        try {

            // Create an EntityManagerFactory and EntityManager
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
            EntityManager em = emf.createEntityManager();

            // Create a new Participant object
            Participants participant = new Participants();
            participant.setChatId(Long.parseLong(chat_id));
            participant.setUserId(Long.parseLong(user_id));
            participant.setAdmin(false);
            participant.setAddedBy(Long.parseLong(added_by));

            // Begin a transaction
            em.getTransaction().begin();

            // Persist the participant object to the database
            em.persist(participant);

            // Commit the transaction
            em.getTransaction().commit();

            // Return the chat_id and a success message
            return new String[]{String.valueOf(participant.getChatId()), "success"};
        } catch (Exception e) {
            e.printStackTrace();
            // Return an error message if an exception occurs
            return new String[]{"Error adding participant", "error"};
        }
    }
}
