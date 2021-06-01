package com.nucleuschess.net;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/")
public class BasicEndpoint {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected, sessionID = " + session.getId());
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("Session " + session.getId() + " closed: " + reason);
    }

    @OnError
    public void onError(Session session, Throwable ex) {
        ex.printStackTrace();
    }
}
