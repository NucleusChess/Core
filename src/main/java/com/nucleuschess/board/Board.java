package com.nucleuschess.board;

import com.google.gson.JsonObject;
import com.nucleuschess.Color;
import com.nucleuschess.Core;
import com.nucleuschess.piece.*;
import jakarta.websocket.Session;

import java.io.IOException;
import java.util.*;

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

    private final Map<Position, Piece> positionPieceMap;

    public Board() {
        this.positionPieceMap = new HashMap<>();
        this.setupBoard();
    }

    public boolean isEmpty(Position position) {
        return positionPieceMap.get(position) == null;
    }

    /**
     * TODO delegate move checking and use that here to verify move before executing it !
     */
    public void move(Piece piece, Position to) {

        if (!piece.hasMoved()) {
            piece.setHasMoved(true);
        }

        final Position from = positionPieceMap.entrySet().stream().filter(e -> e.getValue().equals(piece)).map(Map.Entry::getKey).findFirst().orElseThrow();
        this.setEmpty(from);
        setPiece(piece, to);
    }

    public void setPiece(Piece piece, Position position) {
        positionPieceMap.put(position, piece);

        if (piece == null) return;
        System.out.println("Placed " + piece.getName() + " at " + position.name());
    }

    public void setEmpty(Position position) {
        setPiece(null, position);
    }

    @SuppressWarnings("unchecked")
    public <T extends Piece> T getPiece(Position position) {
        return (T) this.positionPieceMap.get(position);
    }

    public boolean isInCheck(Color color) {
        return false; // implement
    }

    public Position[] getPositionsHorizontally(int rank, int startFile, int endFile) {
        return Arrays.stream(Position.valuesOf(rank)).filter(p -> p.getFileNumber() >= startFile && p.getFileNumber() <= endFile).toArray(Position[]::new);
    }

    public Position[] getPositionsVertically(char file, int startRank, int endRank) {
        return Arrays.stream(Position.valuesOf(file)).filter(p -> p.getRank() >= startRank && p.getRank() <= endRank).toArray(Position[]::new);
    }

    public boolean hasObstructionHorizontally(int rank, int startFile, int endFile) {
        return hasObstruction0(getPositionsHorizontally(rank, startFile, endFile));
    }

    public boolean hasObstructionVertically(char file, int startRank, int endRank) {
        return hasObstruction0(getPositionsVertically(file, startRank, endRank));
    }

    public boolean hasObstructionDiagonally(Position start, Position end) {
        int xChange = end.getFileNumber() - start.getFileNumber();
        int yChange = end.getRank() - start.getRank();

        int steps = Math.abs(start.getRank() - end.getRank());
        int[] direction = getDirection(xChange, yChange);

        for (int i = 0; i < steps; i++) {
            final Position position = Position.valueOf(start.getFileNumber() + direction[0], start.getRank() + direction[1]);
            if (!isEmpty(position)) return true;
        }
        return false;
    }

    private boolean hasObstruction0(Position[] positions) {
        if (positions.length == 2) return false;
        return Arrays.stream(Arrays.copyOfRange(positions, 1, positions.length - 2)).anyMatch(p -> !isEmpty(p));
    }

    private int[] getDirection(int diffX, int diffY) {
        int x = diffX >= 0 ? 1 : 0;
        int y = diffY >= 0 ? 1 : 0;

        return new int[]{x, y};
    }

    private void setupBoard() {
        // PAWNS
        Arrays.stream(Position.valuesOf(2)).forEach(p -> this.setPiece(new Pawn(WHITE), p));
        Arrays.stream(Position.valuesOf(7)).forEach(p -> this.setPiece(new Pawn(BLACK), p));

        // ROOKS
        this.setPiece(new Rook(WHITE), Position.A1);
        this.setPiece(new Rook(WHITE), Position.H1);
        this.setPiece(new Rook(BLACK), Position.A8);
        this.setPiece(new Rook(BLACK), Position.H8);

        // BISHOPS
        this.setPiece(new Bishop(WHITE), Position.C1);
        this.setPiece(new Bishop(WHITE), Position.F1);
        this.setPiece(new Bishop(BLACK), Position.C8);
        this.setPiece(new Bishop(BLACK), Position.F8);

        // KNIGHTS
        this.setPiece(new Knight(WHITE), Position.B1);
        this.setPiece(new Knight(WHITE), Position.G1);
        this.setPiece(new Knight(BLACK), Position.B8);
        this.setPiece(new Knight(BLACK), Position.G8);

        // KINGS
        this.setPiece(new King(WHITE), Position.E1);
        this.setPiece(new King(BLACK), Position.E8);

        // QUEENS
        this.setPiece(new Queen(WHITE), Position.D1);
        this.setPiece(new Queen(BLACK), Position.D8);
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
        class Ansi {
            private static final String RED = "\u001B[31m";
            private static final String BLUE = "\u001B[34m";
            private static final String RESET = "\u001B[0m";
        }

        System.out.println("===================");

        for (int i = 1, j = 8; i <= 8; i++, j--) {
            System.out.print("| ");
            Position[] positions = Position.valuesOf(j);

            Arrays.stream(positions).forEach(p -> {
                if (isEmpty(p)) {
                    System.out.print("- ");
                    return;
                }

                System.out.print((getPiece(p).getColor() == BLACK ? Ansi.BLUE : Ansi.RED) + getPiece(p).getCode() + " " + Ansi.RESET);
            });

            System.out.println("| " + j);
        }

        System.out.println("===================");
        System.out.println("  A B C D E F G H ");
    }
}
