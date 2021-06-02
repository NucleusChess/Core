package com.nucleuschess;

import com.google.gson.Gson;
import com.nucleuschess.board.Board;
import com.nucleuschess.net.BasicEndpoint;
import jakarta.websocket.Session;
import org.glassfish.tyrus.server.Server;

import java.util.HashSet;
import java.util.Set;

public class Core {

    private static final Gson gson = new Gson();
    private static final Set<Session> sessions = new HashSet<>();

    private static Board board;

    public static void main(String[] args) {
        final Server server = new Server("localhost", 9000, "/", null, BasicEndpoint.class);

        try {
            Thread.currentThread().setContextClassLoader(Core.class.getClassLoader());
            server.start();

            board = new Board();
            board.print();

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

    public static void addSession(Session session) {
        sessions.add(session);
    }

    public static void removeSession(Session session) {
        sessions.remove(session);
    }

    public static Set<Session> getSessions() {
        return sessions;
    }

    public static Gson getGson() {
        return gson;
    }
}
