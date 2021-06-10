package com.nucleuschess;

import com.google.gson.Gson;
import com.nucleuschess.board.Board;
import com.nucleuschess.net.BasicEndpoint;
import com.nucleuschess.net.SessionManager;
import jakarta.websocket.Session;
import org.glassfish.tyrus.server.Server;

import java.util.HashSet;
import java.util.Set;

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
public class Core {

    private static final Gson gson = new Gson();

    private static SessionManager sessionManager;
    private static Board board;

    public static void main(String[] args) {
        sessionManager = new SessionManager();

        final Server server = new Server("localhost", 9000, "/", null, BasicEndpoint.class);

        try {
            Thread.currentThread().setContextClassLoader(Core.class.getClassLoader());
            server.start();

            board = new Board();

            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }

    public static Board getBoard() {
        return board;
    }

    public static SessionManager getSessionManager() {
        return sessionManager;
    }

    public static Gson getGson() {
        return gson;
    }
}
