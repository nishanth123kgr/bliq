package com.bliq.services;

import com.bliq.models.Chats;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.core.Response;

import java.util.List;


public class ChatService {

    private final EntityManager em;

    public ChatService(EntityManager em) {
        this.em = em;
    }

    public String[] createChat(String user_id, String receiver_id) {
        try {
            // Create a new Chat object
            Chats chat = new Chats();
            chat.setChatName("Chat between " + user_id + " and " + receiver_id);
            chat.setGroup(false);
            chat.setPrivate(true);
            chat.setCreatedBy(Long.parseLong(user_id));


            // Begin a transaction
            em.getTransaction().begin();

            // Persist the chat object to the database
            em.persist(chat);

            // Commit the transaction
            em.getTransaction().commit();

            // Return the chat_id and a success message
            return new String[]{String.valueOf(chat.getId()), "success"};
        } catch (Exception e) {
            e.printStackTrace();
            // Return an error message if an exception occurs
            return new String[]{"Error creating chat", "error"};
        }
    }

    public String[] createGroup(String user_id, String group_name, Boolean isPrivate) {
        try {
            // Create a new Chat object
            Chats chat = new Chats();
            chat.setChatName(group_name);
            chat.setGroup(true);
            chat.setPrivate(isPrivate);
            chat.setCreatedBy(Long.parseLong(user_id));

            // Begin a transaction
            em.getTransaction().begin();

            // Persist the chat object to the database
            em.persist(chat);

            // Commit the transaction
            em.getTransaction().commit();

            // Return the chat_id and a success message
            return new String[]{String.valueOf(chat.getId()), "success"};
        } catch (Exception e) {
            e.printStackTrace();
            // Return an error message if an exception occurs
            return new String[]{"Error creating chat", "error"};
        }
    }

    public List getGroupsAUserPartOf(String user_id) {
        try {
            // Create an EntityManagerFactory and EntityManager

            String jpql = "SELECT DISTINCT c.id, c.chatName " +
                    "FROM Chats c " +
                    "JOIN Participants p ON c.id = p.chatId " +
                    "WHERE p.userId = :userId " +
                    "AND c.isGroup = true";

            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("userId", Long.parseLong(user_id));

            List<Object[]> results = query.getResultList();

            System.out.println("results: " + results);

            return results;
        } catch (Exception e) {
            e.printStackTrace();
            // Return an error message if an exception occurs
            return null;
        }
    }

    public boolean isUserPartOfChat(String user_id, String group_id) {
        try {
            // Create an EntityManagerFactory and EntityManager

            String jpql = """
                    SELECT p.userId
                    FROM Participants p
                    WHERE p.userId = :userId
                    AND p.chatId = :chatId
                    """;

            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("userId", Long.parseLong(user_id));
            query.setParameter("chatId", Long.parseLong(group_id));

            List<Object[]> results = query.getResultList();

            System.out.println("results: " + results);

            return !results.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            // Return an error message if an exception occurs
            return false;
        }
    }
}
