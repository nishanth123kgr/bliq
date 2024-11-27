package com.bliq.services;

import com.bliq.models.Messages;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class MessageService {
    private EntityManager em;

    public MessageService(EntityManager em) {
        this.em = em;
    }

    public String[] createMessage(String chat_id, String user_id, String message) {
        try {
            // Create a new Message object
            Messages msg = new Messages();
            msg.setChatId(Long.parseLong(chat_id));
            msg.setSenderId(Long.parseLong(user_id));
            msg.setContent(message);

            // Begin a transaction
            em.getTransaction().begin();

            // Persist the message object to the database
            em.persist(msg);

            // Commit the transaction
            em.getTransaction().commit();

            // Return the message_id and a success message
            return new String[]{String.valueOf(msg.getId()), "success"};
        } catch (Exception e) {
            e.printStackTrace();
            // Return an error message if an exception occurs
            return new String[]{"Error creating message", "error"};
        }
    }

    public String[] deleteMessage(String message_id) {
        try {
            // Find the message to delete
            Messages msg = em.find(Messages.class, Long.parseLong(message_id));

            // Begin a transaction
            em.getTransaction().begin();

            // Remove the message from the database
            em.remove(msg);

            // Commit the transaction
            em.getTransaction().commit();

            // Return a success message
            return new String[]{"Message deleted successfully", "success"};
        } catch (Exception e) {
            e.printStackTrace();
            // Return an error message if an exception occurs
            return new String[]{"Error deleting message", "error"};
        }
    }

    public String[] getMessages(String chat_id) {
        try {
            // Create a query to get all messages in a chat
            String jpql = "SELECT m.id, m.senderId, m.content, m.sentAt\n" +
                    "FROM Messages m\n" +
                    "WHERE m.chatId = :chatId\n" +
                    "ORDER BY m.sentAt ASC";
            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("chatId", Long.parseLong(chat_id));
            List<Object[]> results = query.getResultList();

            // Create a JSON array to store the messages
            JSONArray messages = new JSONArray();
            for (Object[] result : results) {
                JSONObject message = new JSONObject();
                message.put("message_id", result[0]);
                message.put("sender_id", result[1]);
                message.put("content", result[2]);
                message.put("created_at", result[3]);
                messages.put(message);
            }

            // Return the messages
            return new String[]{messages.toString(), "success"};
        } catch (Exception e) {
            e.printStackTrace();
            // Return an error message if an exception occurs
            return new String[]{"Error getting messages", "error"};
        }
    }
}
