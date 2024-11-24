package com.bliq.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

import java.util.HashMap;

public class UserService {
    private final EntityManager em;

    public UserService(EntityManager em) {
        this.em = em;
    }

    public String[] checkUserExists(String email) {
        String userExists = null;
        String passwdHash = null;

        try {
            // Create the stored procedure query
            StoredProcedureQuery query = em.createStoredProcedureQuery("CheckUserExists");

            // Register input and output parameters
            query.registerStoredProcedureParameter("p_mail", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_passwd_hash", String.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter("p_user_exists", Boolean.class, ParameterMode.OUT);

            // Set the input parameter
            query.setParameter("p_mail", email);

            // Execute the query
            query.execute();

            // Retrieve the output parameters
            passwdHash = (String) query.getOutputParameterValue("p_passwd_hash");
            if(!(Boolean) query.getOutputParameterValue("p_user_exists")){
                userExists = "false";
            } else {
                userExists = "true";
            }

            // Log or use the outputs as needed
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String[]{passwdHash, userExists};
    }
}

