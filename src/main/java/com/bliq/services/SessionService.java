package com.bliq.services;

import jakarta.persistence.*;

import com.bliq.models.Session;



public class SessionService {
    private final EntityManager em;

    public SessionService(EntityManager em) {
        this.em = em;
    }

    public String[] createSession(int userId, String sessionToken, String device, boolean isActive) {
        try {

            em.getTransaction().begin();
            // Create the stored procedure query
            StoredProcedureQuery query = em.createStoredProcedureQuery("AddSession");

            // Register input and output parameters
            query.registerStoredProcedureParameter("p_session_token", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_user_id", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_is_active", Boolean.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_device", String.class, ParameterMode.IN);

            System.out.println("SessionService: createSession: userId: " + userId);
            System.out.println("SessionService: createSession: sessionToken: " + sessionToken);
            System.out.println("SessionService: createSession: device: " + device);
            System.out.println("SessionService: createSession: isActive: " + isActive);



            // Set the input parameters
            query.setParameter("p_session_token", sessionToken);
            query.setParameter("p_user_id", userId);
            query.setParameter("p_is_active", isActive);
            query.setParameter("p_device", device);



            // Execute the query
            query.execute();

            em.getTransaction().commit();

            return new String[]{"Session created successfully", "success"};

        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Session creation failed\n\n"+ e, "error"};
        }
    }
}
