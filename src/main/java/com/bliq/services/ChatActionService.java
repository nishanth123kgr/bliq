package com.bliq.services;

import com.bliq.models.ChatActions;
import jakarta.persistence.EntityManager;

public class ChatActionService {
    private EntityManager em;

    public ChatActionService(EntityManager em) {
        this.em = em;
    }

    public String[] createAction(String action, String chat_id, String user_id, String done_by) {
        try {
            em.getTransaction().begin();
            ChatActions chatActions = new ChatActions();
            chatActions.setAction(Long.valueOf(action));
            chatActions.setChatId(Long.valueOf(chat_id));
            chatActions.setUserId(Long.valueOf(user_id));
            chatActions.setDoneBy(Long.valueOf(done_by));
            em.persist(chatActions);
            em.getTransaction().commit();
            return new String[]{"Chat action created successfully", "success"};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Error creating chat action", "error"};

        }
    }
}
