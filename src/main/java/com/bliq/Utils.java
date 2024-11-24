package com.bliq;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    public String hashPassword(String password) {
        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Hash the password
            byte[] hashedBytes = md.digest(password.getBytes());

            // Convert byte array into a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public boolean checkPassword(String password, String hash) {
        return hashPassword(password).equals(hash);
    }

}


