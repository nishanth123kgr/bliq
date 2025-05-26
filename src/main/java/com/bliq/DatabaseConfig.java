package com.bliq;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConfig {
    
    private static EntityManagerFactory emf;
    
    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            Map<String, String> properties = new HashMap<>();
            
            // Get database configuration from environment variables
            String databaseUrl = System.getenv("DATABASE_URL");
            String databaseUser = System.getenv("DATABASE_USER");
            String databasePassword = System.getenv("DATABASE_PASSWORD");
            
            // Use Clever Cloud MySQL database if environment variables are not set
            if (databaseUrl == null) {
                databaseUrl = "jdbc:mysql://bhnnqi71g2wqrpwustfv-mysql.services.clever-cloud.com:3306/bhnnqi71g2wqrpwustfv";
            }
            if (databaseUser == null) {
                databaseUser = "uqrllnmuff3ztkgj";
            }
            if (databasePassword == null) {
                databasePassword = "2ZFEDD7YO5EnzHRYN5QJ";
            }
            
            // Override persistence.xml properties with environment variables
            properties.put("jakarta.persistence.jdbc.url", databaseUrl);
            properties.put("jakarta.persistence.jdbc.user", databaseUser);
            properties.put("jakarta.persistence.jdbc.password", databasePassword);
            
            emf = Persistence.createEntityManagerFactory("bliq", properties);
        }
        return emf;
    }
    
    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
