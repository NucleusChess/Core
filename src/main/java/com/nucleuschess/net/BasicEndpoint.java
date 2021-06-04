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
        System.out.println("[" + session.getId() + "] Connected");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("[" + session.getId() + "] " + message);

        if (message.matches("\\{.+}")) {
            final JsonObject obj = Core.getGson().fromJson(message, JsonObject.class);

            switch (obj.get("position").getAsString()) {
                case "e4":
                    Core.getBoard().move(session.getId(), "P", "c5");
                    break;
                case "f3":
                    Core.getBoard().move(session.getId(), "P", "d6");
                    break;
                case "cxd4":
                    Core.getBoard().move(session.getId(), "P", "c5");
                    break;
                case "Nxd4":
                    Core.getBoard().move(session.getId(), "N", "f5");
                    break;
                case "c3":
                    Core.getBoard().move(session.getId(), "P", "a6");
                    break;
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
