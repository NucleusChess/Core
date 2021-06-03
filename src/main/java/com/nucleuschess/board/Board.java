package com.nucleuschess.board;

import com.google.gson.JsonObject;
import com.nucleuschess.Color;
import com.nucleuschess.Core;
import com.nucleuschess.piece.*;
import com.nucleuschess.util.observer.ObservableSet;
import jakarta.websocket.Session;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.nucleuschess.Color.BLACK;
import static com.nucleuschess.Color.WHITE;

/*
  Copyright (C) 2020-2021, Wouter Kistemaker.
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

/**
 * Class representing the Chess-board, with all the squares gathered in a {@link Set<Position>}
 *
 * @author Wouter Kistemaker
 * @since 1.0-SNAPSHOT
 */
public final class Board {

    private final ObservableSet<Position> positions;

    public Board() {
        this.positions = new ObservableSet<>();
        this.setupBoard();

        positions.addHandler(p -> print());

        // Comically, this is supposed to fire the observable-handlers. Except it doesn't because the Set does not change but the object inside the set does.
        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            getPosition('e', 4).setPiece(new Pawn(BLACK));
            getPosition('e', 2).setPiece(null);

            getPosition('e', 5).setPiece(new Pawn(WHITE));
            getPosition('e', 7).setPiece(null);
        }, 5, TimeUnit.SECONDS);
    }

    public final Position getPosition(char file, int rank) {
        return positions.stream().filter(p -> p.getFile() == file && p.getRank() == rank).findFirst().orElseThrow(NullPointerException::new);
    }

    public final Position getPosition(int file, int rank) {
        return getPosition(PositionUtility.getFile(file), rank);
    }

    public final Position[] getPositions(char file) {
        return positions.stream().filter(p -> p.getFile() == file).toArray(Position[]::new);
    }

    public final Position[] getPositions(int rank) {
        return positions.stream().filter(p -> p.getRank() == rank).toArray(Position[]::new);
    }

    public final Set<Position> getPositions() {
        return new HashSet<>(positions);
    }

    public final Position[] getEmptyPositions() {
        return positions.stream().filter(Position::isEmpty).toArray(Position[]::new);
    }

    public boolean isInCheck(Color color) {
        return false; // implement
    }

    public Position[] getPositionsHorizontally(int rank, int startFile, int endFile) {
        return positions.stream().filter(p -> p.getRank() == rank)
                .filter(p -> PositionUtility.getFileNumber(p.getFile()) >= startFile)
                .filter(p -> PositionUtility.getFileNumber(p.getFile()) <= endFile).toArray(Position[]::new);
    }

    public Position[] getPositionsVertically(int file, int startRank, int endRank) {
        return positions.stream().filter(p -> PositionUtility.getFileNumber(p.getFile()) == file)
                .filter(p -> p.getRank() >= startRank)
                .filter(p -> p.getRank() <= endRank).toArray(Position[]::new);
    }

    public boolean hasObstructionHorizontally(int rank, int startFile, int endFile) {
        return hasObstruction0(getPositionsHorizontally(rank, startFile, endFile));
    }

    public boolean hasObstructionVertically(int file, int startRank, int endRank) {
        return hasObstruction0(getPositionsVertically(file, startRank, endRank));
    }

    public boolean hasObstructionDiagonally(Position start, Position end) {
        int xChange = end.getFileNumber() - start.getFileNumber();
        int yChange = end.getRank() - start.getRank();

        int steps = Math.abs(start.getRank() - end.getRank());
        int[] direction = getDirection(xChange, yChange);

        for (int i = 0; i < steps; i++) {
            final Position position = getPosition(start.getFileNumber() + direction[0], start.getRank() + direction[1]);
            if (!position.isEmpty()) return true;
        }
        return false;
    }

    private boolean hasObstruction0(Position[] positions) {
        if (positions.length == 2) return false;
        return Arrays.stream(Arrays.copyOfRange(positions, 1, positions.length - 2)).anyMatch(p -> !p.isEmpty());
    }

    private int[] getDirection(int diffX, int diffY) {
        int x = diffX >= 0 ? 1 : 0;
        int y = diffY >= 0 ? 1 : 0;

        return new int[]{x, y};
    }

    private void setupBoard() {
        for (int i = 1; i < 9; i++) { // more convenient, we use the numbers 1-8 since these are the actual file numbers
            for (int j = 1; j < 9; j++) { // same thing applies here
                Color color = ((i - 1) % 2 == 0) ? ((j - 1) % 2 == 0 ? BLACK : WHITE) : ((j - 1) % 2 == 0 ? WHITE : BLACK);
                this.positions.add(new Position(PositionUtility.getFile(i), j, color));
            }
        }

        // PAWNS
        Arrays.stream(getPositions(2)).forEach(p -> p.setPiece(new Pawn(WHITE)));
        Arrays.stream(getPositions(7)).forEach(p -> p.setPiece(new Pawn(BLACK)));

        // ROOKS
        this.getPosition('a', 1).setPiece(new Rook(WHITE));
        this.getPosition('h', 1).setPiece(new Rook(WHITE));
        this.getPosition('a', 8).setPiece(new Rook(BLACK));
        this.getPosition('h', 8).setPiece(new Rook(BLACK));

        // BISHOPS
        this.getPosition('c', 1).setPiece(new Bishop(WHITE));
        this.getPosition('f', 1).setPiece(new Bishop(WHITE));
        this.getPosition('c', 8).setPiece(new Bishop(BLACK));
        this.getPosition('f', 8).setPiece(new Bishop(BLACK));

        // KNIGHTS
        this.getPosition('b', 1).setPiece(new Knight(WHITE));
        this.getPosition('g', 1).setPiece(new Knight(WHITE));
        this.getPosition('b', 8).setPiece(new Knight(BLACK));
        this.getPosition('g', 8).setPiece(new Knight(BLACK));

        // KINGS
        this.getPosition('e', 1).setPiece(new King(WHITE));
        this.getPosition('e', 8).setPiece(new King(WHITE));

        // QUEENS
        this.getPosition('d', 1).setPiece(new Queen(WHITE));
        this.getPosition('d', 8).setPiece(new Queen(BLACK));

        this.getPositions().stream().filter(p -> !p.isEmpty()).forEach(p -> p.getPiece().setHasMoved(false));
    }

    // -----------------------------------------------------------------

    public void move(String userID, String piece, String position) {
        final Optional<Session> optSession = Core.getSessions().stream().filter(s -> s.getId().equalsIgnoreCase(userID)).findAny();

        if (optSession.isEmpty()) {
            throw new IllegalStateException("No session for specified user-id.");
        }

        try {
            final JsonObject obj = new JsonObject();
            obj.addProperty("position", position);
            obj.addProperty("piece", piece);
            obj.addProperty("color", optSession.get().getUserProperties().get("color").toString());

            final String json = Core.getGson().toJson(obj);

            optSession.get().getBasicRemote().sendText(json);
            System.out.println("Sent move (" + position + ", " + piece + ") to " + userID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        System.out.println("===================");

        for (int i = 1, j = 8; i <= 8; i++, j--) {
            System.out.print("| ");
            Position[] positions = getPositions(i);

            Arrays.stream(positions).forEach(p -> {
                if (p.getPiece() == null) {
                    System.out.print("- ");
                    return;
                }

                System.out.print(p.getPiece().getCode() + " ");
            });

            System.out.println("| " + j);
        }

        System.out.println("===================");
        System.out.println("  A B C D E F G H ");
    }
}
