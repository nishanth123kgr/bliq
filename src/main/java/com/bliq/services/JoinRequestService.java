package com.bliq.services;

import jakarta.persistence.EntityManager;

public class JoinRequestService {
    private EntityManager em;

    public JoinRequestService(EntityManager em) {
        this.em = em;
    }

    public String[] createJoinRequest(String user_id, String chat_id) {

        return new String[]{"", ""};
    }


}
