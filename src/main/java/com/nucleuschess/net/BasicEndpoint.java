package com.nucleuschess.net;

import com.nucleuschess.Core;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;

@ServerEndpoint("/")
public class BasicEndpoint {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("[" + session.getId() + "] Connected");

        Core.addSession(session);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println("[" + session.getId() + "] " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("[" + session.getId() + "] Disconnected: " + reason);
    }

    @OnError
    public void onError(Session session, Throwable ex) {
        ex.printStackTrace(System.err);
    }
}
