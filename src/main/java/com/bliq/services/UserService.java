package com.bliq.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

import com.bliq.models.Users;

import java.util.List;


public class UserService {
    private final EntityManager em;

    public UserService(EntityManager em) {
        this.em = em;
    }

    public String[] createUser(String name, String email, String passwdHash) {
        try{
            Users user = new Users();
            user.setMail(email);
            user.setName(name);
            user.setPaswdHash(passwdHash);
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return new String[]{"User created successfully", "success"};
        } catch (Exception e) {
            return new String[]{"User creation failed\n\n"+ e, "error"};
        }
    }

    public String[] checkUserExists(String email) {
        String userExists = null;
        String passwdHash = null;
        int userId = 0;

        try {
            // Create the stored procedure query
            StoredProcedureQuery query = em.createStoredProcedureQuery("CheckUserExists");

            // Register input and output parameters
            query.registerStoredProcedureParameter("p_mail", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_passwd_hash", String.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter("p_user_exists", Boolean.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter("p_user_id", Integer.class, ParameterMode.OUT);

            // Set the input parameter
            query.setParameter("p_mail", email);

            // Execute the query
            query.execute();

            // Retrieve the output parameters
            passwdHash = (String) query.getOutputParameterValue("p_passwd_hash");
            userId = (Integer) query.getOutputParameterValue("p_user_id");

            if(!(Boolean) query.getOutputParameterValue("p_user_exists")){
                userExists = "false";
            } else {
                userExists = "true";
            }

            // Log or use the outputs as needed
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String[]{passwdHash, userExists, String.valueOf(userId)};
    }

    public String[] getUserDetails(int user_id){

        try {
            // Create the stored procedure query
            StoredProcedureQuery query = em.createStoredProcedureQuery("GetUserDetails");

            // Register input and output parameters
            query.registerStoredProcedureParameter("p_user_id", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_name", String.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter("p_mail", String.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter("p_status", Integer.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter("p_is_admin", Boolean.class, ParameterMode.OUT);

            // Set the input parameter
            query.setParameter("p_user_id", user_id);

            // Execute the query
            query.execute();

            // Retrieve the output parameters
            String name = (String) query.getOutputParameterValue("p_name");
            String mail = (String) query.getOutputParameterValue("p_mail");
            int status = (Integer) query.getOutputParameterValue("p_status");
            boolean isAdmin = (Boolean) query.getOutputParameterValue("p_is_admin");

            return new String[]{name, mail, String.valueOf(status), String.valueOf(isAdmin)};
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String[]{"User does not exist", "error"};
    }

    public List searchUsers(String searchQuery){
        try {
            // Create the stored procedure query
            StoredProcedureQuery query = em.createStoredProcedureQuery("SearchUsers");

            // Register input and output parameters
            query.registerStoredProcedureParameter("p_search_query", String.class, ParameterMode.IN);

            // Set the input parameter
            query.setParameter("p_search_query", searchQuery);

            // Execute the query
            query.execute();

            // Retrieve the output parameters
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String[] updateUserStatus(String user_id, String status){
        try {
            em.getTransaction().begin();
            // Create the stored procedure query
            StoredProcedureQuery query = em.createStoredProcedureQuery("UpdateUserStatus");

            // Register input and output parameters
            query.registerStoredProcedureParameter("p_user_id", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_status", Integer.class, ParameterMode.IN);

            System.out.println("UserService: updateUserStatus: userId: " + user_id);
            System.out.println("UserService: updateUserStatus: status: " + status);


            // Set the input parameters
            query.setParameter("p_user_id", Integer.parseInt(user_id));
            query.setParameter("p_status", Integer.parseInt(status));

            // Execute the query
            query.execute();

            System.out.println("UserService: updateUserStatus: query: " + query);


            em.getTransaction().commit();

            System.out.println("UserService: updateUserStatus: success");

            return new String[]{"User status updated successfully", "success"};
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String[]{"User status update failed", "error"};
    }

    public boolean userExists(String userId) {
        try {
            Users user = em.find(Users.class, Long.parseLong(userId));
            return user != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Users getUser(String userId) {
        try {
            return em.find(Users.class, Long.parseLong(userId));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

