package com.bliq.services;

import com.bliq.models.NotificationRecipients;
import jakarta.persistence.EntityManager;

public class NotificationRecipientsService {
    EntityManager em;

    public NotificationRecipientsService(EntityManager em) {
        this.em = em;
    }

    public String[] getNotificationRecipients(String user_id) {
        return new String[]{"", ""};
    }

    public String[] addNotificationRecipient(String recipient_id, String notification_id) {
        try {
            em.getTransaction().begin();



            NotificationRecipients notificationRecipient = new NotificationRecipients();
            notificationRecipient.setRecipientId(Long.valueOf(recipient_id));
            notificationRecipient.setNotificationId(Long.valueOf(notification_id));
            em.persist(notificationRecipient);


            em.getTransaction().commit();
            return new String[]{"Notification recipient added successfully", "success"};
        } catch (Exception e) {
            return new String[]{"Error adding notification recipient", "error"};
        }

    }

    public String[] removeNotificationRecipient(String user_id, String recipient_id) {
        return new String[]{"", ""};
    }

    public String[] setSeenStatus(String user_id, String recipient_id, String seen_status) {
        return new String[]{"", ""};
    }
}
