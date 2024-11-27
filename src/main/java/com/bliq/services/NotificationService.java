package com.bliq.services;

import com.bliq.models.NotificationRecipients;
import com.bliq.models.Participants;
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
            String jpql = "SELECT n FROM Notifications n WHERE n.sendTo = :user_id";
            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("user_id", Long.parseLong(user_id));
            List<Object[]> notifications = query.getResultList();

            JSONArray jsonNotifications = new JSONArray();
            for (Object[] notification : notifications) {
                System.out.println(Arrays.toString(notification));
                JSONObject jsonNotification = new JSONObject();
                jsonNotification.put("id", notification[0]);
                jsonNotification.put("send_to", notification[1]);
                System.out.println(notification[1]);
                jsonNotification.put("req_id", notification[2]);
                jsonNotification.put("msg_id", notification[3]);
                jsonNotification.put("type", notification[4]);
                jsonNotifications.put(jsonNotification);
            }


            return new String[]{notifications.toString(), "success"};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Error retrieving notifications", "error"};
        }
    }



}
