package com.nucleuschess.net;

import com.nucleuschess.Core;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

/*
  Copyright (C) 2021, NucleusChess.
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU Affero General Public License as published
  by the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Affero General Public License for more details.
  You should have received a copy of the GNU Affero General Public License
  along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
@ServerEndpoint("/")
public class BasicEndpoint {

    @OnOpen
    public void onOpen(Session session) {
        Core.getSessionManager().addSession(session);
        System.out.println("[" + session.getId() + "] Connected");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("[" + session.getId() + "] " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        Core.getSessionManager().removeSession(session);
        System.out.println("[" + session.getId() + "] Disconnected: " + reason);
    }

    @OnError
    public void onError(Session session, Throwable ex) {
        ex.printStackTrace(System.err);
    }
}
