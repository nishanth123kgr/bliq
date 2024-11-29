package com.bliq.services;

import com.bliq.models.Messages;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.json.JSONArray;
import org.json.JSONObject;
import com.bliq.webSocket.Socket;

import java.text.SimpleDateFormat;
import java.util.List;

public class MessageService {
    private final EntityManager em;

    public MessageService(EntityManager em) {
        this.em = em;
    }

    public String[] createMessage(String chat_id, String user_id, String message) {
        try {

            if(message.length() > 1000) {
                return new String[]{"Message too long", "error"};
            }

            if(message.isEmpty()) {
                return new String[]{"Message cannot be empty", "error"};
            }

            System.out.println("chat_id: " + chat_id);
            System.out.println("user_id: " + user_id);
            System.out.println("message: " + message);

            ChatService chatService = new ChatService(em);

            if(!chatService.isUserPartOfChat(user_id, chat_id)) {
                return new String[]{"User is not part of chat", "error"};
            }


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

            try {
                NotificationService notificationService = new NotificationService(em);
                notificationService.createNotification(String.valueOf(msg.getId()), "0", user_id);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message_id", msg.getId());
                jsonObject.put("chat_id", chat_id);
                jsonObject.put("sender_id", user_id);
                jsonObject.put("content", message);
                jsonObject.put("created_at", new SimpleDateFormat("hh:mm a yyyy-MM-dd").format(msg.getSentAt()));
                jsonObject.put("sender_name", new UserService(em).getUser(user_id).getName());
                jsonObject.put("is_group", chatService.isGroup(chat_id));
                jsonObject.put("type", "message");

                Socket.broadcast(chat_id, jsonObject.toString());

            }
            catch (Exception e) {
                e.printStackTrace();
                return new String[]{"Error creating notification", "error"};
            }



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
            String jpql = """
                    SELECT m.id, m.senderId, m.content, m.sentAt, u.name
                    FROM Messages m
                    join Users u on m.senderId = u.id
                    WHERE m.chatId = :chatId
                    ORDER BY m.sentAt ASC""";
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
                message.put("sender_name", result[4]);
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

    public String[] getMessage(String message_id) {
        try {
            // Create a query to get a message by its id
            String jpql = """
                    SELECT m.id, m.senderId, m.content, m.sentAt
                    FROM Messages m
                    WHERE m.id = :messageId""";
            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("messageId", Long.parseLong(message_id));
            List<Object[]> results = query.getResultList();

            // Create a JSON object to store the message
            JSONObject message = new JSONObject();
            for (Object[] result : results) {
                message.put("message_id", result[0]);
                message.put("sender_id", result[1]);
                message.put("content", result[2]);
                message.put("created_at", result[3]);
            }

            // Return the message
            return new String[]{message.toString(), "success"};
        } catch (Exception e) {
            e.printStackTrace();
            // Return an error message if an exception occurs
            return new String[]{"Error getting message", "error"};
        }
    }

    public boolean messageExists(String messageId) {
        try {
            String jpql = """
                    SELECT m.id
                    FROM Messages m
                    WHERE m.id = :messageId""";
            TypedQuery<Object> query = em.createQuery(jpql, Object.class);
            query.setParameter("messageId", Long.parseLong(messageId));
            List<Object> results = query.getResultList();

            return !results.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            // Return false if an exception occurs
            return false;
        }
    }
}
