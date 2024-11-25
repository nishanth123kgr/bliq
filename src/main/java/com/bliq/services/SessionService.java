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

    public String[] checkSessionExists(String sessionToken) {
        String sessionExists = null;
        int userId = 0;

        try {
            // Create the stored procedure query
            StoredProcedureQuery query = em.createStoredProcedureQuery("CheckSessionExists");

            // Register input and output parameters
            query.registerStoredProcedureParameter("p_session_token", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_user_id", Integer.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter("p_session_exists", Boolean.class, ParameterMode.OUT);

            // Set the input parameter
            query.setParameter("p_session_token", sessionToken);

            // Execute the query
            query.execute();

            // Retrieve the output parameters
            userId = (Integer) query.getOutputParameterValue("p_user_id");

            if(!(Boolean) query.getOutputParameterValue("p_session_exists")){
                sessionExists = "false";
            } else {
                sessionExists = "true";
            }



        } catch (Exception e) {
            System.out.println(e);
        }
        return new String[]{sessionExists, Integer.toString(userId)};
    }
}
