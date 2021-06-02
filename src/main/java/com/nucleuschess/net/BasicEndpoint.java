package com.nucleuschess.net;

import com.google.gson.JsonObject;
import com.nucleuschess.Color;
import com.nucleuschess.Core;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/")
public class BasicEndpoint {

    @OnOpen
    public void onOpen(Session session) {
        Core.addSession(session);
        session.getUserProperties().put("color", Math.random() <= .5 ? Color.BLACK : Color.WHITE);

        System.out.println("[" + session.getId() + "] Connected");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("[" + session.getId() + "] " + message);

        if (message.matches("\\{.+}")) {
            final JsonObject obj = Core.getGson().fromJson(message, JsonObject.class);

            if (obj.get("position").getAsString().equalsIgnoreCase("e4")) {
                Core.getBoard().move(session.getId(), "pawn", "e5");
            }
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        Core.removeSession(session);
        System.out.println("[" + session.getId() + "] Disconnected: " + reason);
    }

    @OnError
    public void onError(Session session, Throwable ex) {
        ex.printStackTrace(System.err);
    }
}
