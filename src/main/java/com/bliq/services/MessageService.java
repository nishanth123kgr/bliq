package com.bliq.services;

import com.bliq.models.Messages;
import jakarta.persistence.EntityManager;

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
}
