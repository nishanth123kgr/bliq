package com.bliq.webSocket;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/group/{chatId}")
public class Socket {

    // Store sessions for each chatId
    private static final Map<String, Set<Session>> chatRooms = new ConcurrentHashMap<>();

    private String currentChatId;

    @OnOpen
    public void onOpen(@PathParam("chatId") String chatId, Session session) {
        this.currentChatId = chatId;

        chatRooms.computeIfAbsent(chatId, k -> ConcurrentHashMap.newKeySet()).add(session);
        System.out.println("joined chatId: " + chatId);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
//        broadcast(currentChatId,  message);
//        System.out.println();
    }

    @OnClose
    public void onClose(Session session) {
        Set<Session> sessions = chatRooms.getOrDefault(currentChatId, Collections.emptySet());
        sessions.remove(session);
        if (sessions.isEmpty()) {
            chatRooms.remove(currentChatId);
        }
        System.out.println("left chatId: " + currentChatId);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Error in session for chatId: " + currentChatId);
        throwable.printStackTrace();
    }

    public static void broadcast(String chatId, String message) {
        chatRooms.getOrDefault(chatId, Collections.emptySet())
                .forEach(session -> {
                    try {
                        session.getBasicRemote().sendText(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
