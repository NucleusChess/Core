package com.nucleuschess.net;

import com.nucleuschess.board.Position;
import com.nucleuschess.piece.Piece;
import jakarta.websocket.Session;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public final class SessionManager {

    private final Set<Session> sessions = new HashSet<>();
    private final Queue<NetMove> queue = new LinkedBlockingQueue<>();

    public SessionManager() { }

    public void addSession(Session session) {
        this.sessions.add(session);
    }

    public void removeSession(Session session) {
        this.sessions.remove(session);
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public Queue<NetMove> getQueue() {
        return queue;
    }

    public static class NetMove {
        private final Session session;
        private final Position from;
        private final Position to;
        private final Piece piece;

        public NetMove(Session session, Position from, Position to, Piece piece) {
            this.session = session;
            this.from = from;
            this.to = to;
            this.piece = piece;
        }
    }
}
