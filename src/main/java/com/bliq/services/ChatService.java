package com.bliq.services;

import com.bliq.models.Chats;
import jakarta.persistence.EntityManager;


public class ChatService {

    private EntityManager em;

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

    public String[] createGroup(String user_id, String group_name) {
        try {
            // Create a new Chat object
            Chats chat = new Chats();
            chat.setChatName(group_name);
            chat.setGroup(true);
            chat.setPrivate(false);
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
}
