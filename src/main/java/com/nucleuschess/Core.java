package com.nucleuschess;

import jakarta.websocket.DeploymentException;
import org.glassfish.tyrus.server.Server;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

public final class Core {

    public static void main(String[] args) {
        final Server server = new Server("localhost", 9000, "/", null, Endpoint.class);

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }

    @ServerEndpoint("/")
    private static final class Endpoint {
        @OnOpen
        public void onOpen(Session session) {
            System.out.println("Connected, sessionID = " + session.getId());
        }

        @OnClose
        public void onClose(Session session, CloseReason reason) {
            System.out.println("Session " + session.getId() + " closed: " + reason);
        }
    }
}
