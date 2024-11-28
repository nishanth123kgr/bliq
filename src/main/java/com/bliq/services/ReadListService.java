package com.bliq.services;

import com.bliq.models.ReadList;
import jakarta.persistence.*;

import java.util.List;

public class ReadListService {
    private EntityManager em;

    public ReadListService(EntityManager em) {
        this.em = em;
    }

    public boolean isRead(String message_id, String user_id) {
        try {
            String jpql = "SELECT r FROM ReadList r WHERE r.messageId = :messageId AND r.readBy = :readBy";
            TypedQuery<ReadList> query = em.createQuery(jpql, ReadList.class);
            query.setParameter("messageId", Long.parseLong(message_id));
            query.setParameter("readBy", Long.parseLong(user_id));
            List<ReadList> results = query.getResultList();
            return !results.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String[] addReadStatus(String message_id, String user_id) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bliq");
            EntityManager em = emf.createEntityManager();

            if(isRead(message_id, user_id)) {
                return new String[]{"User has already read the message "+message_id, "success"};
            }


            System.out.println("Read message_id: " + message_id);
            em.getTransaction().begin();
            ReadList readList = new ReadList();
            readList.setMessageId(Long.valueOf(message_id));
            readList.setReadBy(Long.valueOf(user_id));
            em.persist(readList);
            em.getTransaction().commit();

            return new String[]{"User has read the message "+message_id, "success"};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Error adding into read_list "+user_id, "error"};
        }
    }

    public List<ReadList> getReadList(String message_id) {
        try {
            String jpql = "SELECT r FROM ReadList r WHERE r.messageId = :messageId";
            TypedQuery<ReadList> query = em.createQuery(jpql, ReadList.class);
            query.setParameter("messageId", Long.parseLong(message_id));
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
