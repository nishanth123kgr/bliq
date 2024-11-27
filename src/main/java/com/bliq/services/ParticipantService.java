package com.bliq.services;

import com.bliq.models.Participants;
import jakarta.persistence.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class ParticipantService {
    private EntityManager em;

    public ParticipantService(EntityManager em) {
        this.em = em;
    }

    public Boolean isParticipant(String chat_id, String user_id) {
        try {
            // Create a query to check if a user is a participant in a chat
            String jpql = "SELECT p FROM Participants p WHERE p.chatId = :chatId AND p.userId = :userId";
            return executeParticipantQuery(chat_id, user_id, jpql);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isUserAdmin(String chat_id, String user_id) {
        try {
            // Create a query to check if a user is an admin in a chat
            String jpql = "SELECT p FROM Participants p WHERE p.chatId = :chatId AND p.userId = :userId AND p.isAdmin= TRUE";
            return executeParticipantQuery(chat_id, user_id, jpql);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean executeParticipantQuery(String chat_id, String user_id, String jpql) {
        TypedQuery<Participants> query = em.createQuery(jpql, Participants.class);
        query.setParameter("chatId", Long.parseLong(chat_id));
        query.setParameter("userId", Long.parseLong(user_id));
        List<Participants> results = query.getResultList();
        return !results.isEmpty();
    }

    public String[] addParticipant(String chat_id, String user_id, String added_by, Boolean isAdmin) {
        try {

            // Create an EntityManagerFactory and EntityManager
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
            EntityManager em = emf.createEntityManager();

            // Create a new Participant object
            Participants participant = new Participants();
            participant.setChatId(Long.parseLong(chat_id));
            participant.setUserId(Long.parseLong(user_id));
            participant.setAdmin(isAdmin);
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

    public List getParticipationsOfUser(String user_id) {
        try {
            // Create an EntityManagerFactory and EntityManager

            String jpql = "SELECT DISTINCT p.userId, p.chatId, u.name, u.status\n" +
                    "FROM Participants p\n" +
                    "JOIN Users u ON p.userId = u.id\n" +
                    "JOIN Chats c ON p.chatId = c.id\n" +
                    "WHERE p.chatId IN (\n" +
                    "    SELECT DISTINCT sub.chatId\n" +
                    "    FROM Participants sub\n" +
                    "    WHERE sub.userId = :userId\n" +
                    ")\n" +
                    "AND p.userId != :userId\n" +
                    "AND c.isGroup = FALSE";

            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("userId", user_id);

            List<Object[]> results = query.getResultList();

            System.out.println("results: " + results);

            return results;
        } catch (Exception e) {
            e.printStackTrace();
            // Return an error message if an exception occurs
            return null;
        }
    }

    public List<Participants> getUsersToBeNotified(Long messageId) {
        try {
            String jpql = "SELECT p FROM Participants p WHERE p.chatId = (SELECT m.chatId FROM Messages m WHERE m.id = :messageId)";
            TypedQuery<Participants> query = em.createQuery(jpql, Participants.class);
            query.setParameter("messageId", messageId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Participants> getAdminsToBeNotified(Long chat_id) {
        try {
            String jpql = "SELECT p FROM Participants p WHERE p.chatId = :chatId AND p.isAdmin = TRUE";
            TypedQuery<Participants> query = em.createQuery(jpql, Participants.class);
            query.setParameter("chatId", chat_id);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
