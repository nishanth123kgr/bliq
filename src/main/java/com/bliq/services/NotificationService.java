package com.bliq.services;

import com.bliq.models.NotificationRecipients;
import com.bliq.models.Participants;
import com.bliq.models.ReadList;
import jakarta.persistence.EntityManager;

import com.bliq.models.Notifications;
import jakarta.persistence.TypedQuery;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public class NotificationService {
    EntityManager em;

    public NotificationService(EntityManager em) {
        this.em = em;
    }

    public String[] createNotification(String mes_req_id, String type, String user_id) {
        try {
            em.getTransaction().begin();
            Notifications notification = new Notifications();
            if(type.equals("1")) {
                notification.setReqId(Long.valueOf(mes_req_id));
            } else if(type.equals("0")) {
                notification.setMsgId(Long.valueOf(mes_req_id));
            }
            notification.setType(Long.valueOf(type));
            em.persist(notification);
            em.getTransaction().commit();

            if(type.equals("0")){
                ParticipantService participantService = new ParticipantService(em);
                try{
                    List<Participants> participants = participantService.getUsersToBeNotified(Long.valueOf(mes_req_id));
                    sendNotifications(user_id, notification, participants);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new String[]{"Error Adding notification Recipients", "error"};
                }
            } else if (type.equals("1")) {
                ParticipantService participantService = new ParticipantService(em);
                try{
                    List<Participants> participants = participantService.getAdminsToBeNotified(Long.valueOf(mes_req_id));
                    sendNotifications(user_id, notification, participants);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new String[]{"Error Adding notification Recipients", "error"};
                }

            }

            return new String[]{"Notification created successfully", "success"};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Error creating notification", "error"};
        }
    }

    private void sendNotifications(String user_id, Notifications notification, List<Participants> participants) {
        for(Participants participant : participants) {
            if(participant.getUserId() == Long.parseLong(user_id)) {
                continue;
            }
            NotificationRecipients notificationRecipient = new NotificationRecipients();
            notificationRecipient.setRecipientId(participant.getUserId());
            notificationRecipient.setNotificationId(notification.getId());
            em.getTransaction().begin();
            em.persist(notificationRecipient);
            em.getTransaction().commit();
        }
    }

    public String[] deleteNotification(String notification_id) {
        try {
            em.getTransaction().begin();
            em.createNativeQuery("DELETE FROM notifications WHERE id = ?")
                    .setParameter(1, notification_id)
                    .executeUpdate();
            em.getTransaction().commit();
            return new String[]{"Notification deleted successfully", "success"};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Error deleting notification", "error"};
        }
    }

    public String[] getNotifications(String user_id) {
        try {
            String jpql = "select n from Notifications n where n.id IN (SELECT r.notificationId from NotificationRecipients r WHERE r.recipientId = :user_id)";
            TypedQuery<Notifications> query = em.createQuery(jpql, Notifications.class);
            query.setParameter("user_id", Long.parseLong(user_id));
            List<Notifications> notifications = query.getResultList();

            ReadListService readListService = new ReadListService(em);

            JSONArray jsonNotifications = new JSONArray();

            for (Notifications notification : notifications) {
                JSONObject jsonNotification = new JSONObject();
                jsonNotification.put("id", notification.getId());
                jsonNotification.put("req_id", notification.getReqId());
                jsonNotification.put("msg_id", notification.getMsgId());
                jsonNotification.put("type", notification.getType());
                jsonNotifications.put(jsonNotification);

                if(notification.getType() == 0){
                    try {
                        readListService.addReadStatus(String.valueOf(notification.getMsgId()), user_id);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        return new String[]{"Error adding read status ", "error"};
                    }
                }
            }

            System.out.println("Notifications: " + jsonNotifications);


            return new String[]{jsonNotifications.toString(), "success"};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Error retrieving notifications", "error"};
        }
    }



}
