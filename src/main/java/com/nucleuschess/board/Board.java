package com.nucleuschess.board;

import com.google.gson.JsonObject;
import com.nucleuschess.Color;
import com.nucleuschess.Core;
import com.nucleuschess.move.Move;
import com.nucleuschess.move.checker.*;
import com.nucleuschess.move.finder.*;
import com.nucleuschess.piece.*;
import jakarta.websocket.Session;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    @SuppressWarnings("FieldMayBeFinal")
    private int moveCounter;

    private final Map<Position, Piece> positionPieceMap;

    // Move checkers
    private final KingMoveChecker kingMoveChecker;
    private final QueenMoveChecker queenMoveChecker;
    private final RookMoveChecker rookMoveChecker;
    private final BishopMoveChecker bishopMoveChecker;
    private final KnightMoveChecker knightMoveChecker;
    private final PawnMoveChecker pawnMoveChecker;

    // Move finders
    private final KingMoveFinder kingMoveFinder;
    private final QueenMoveFinder queenMoveFinder;
    private final RookMoveFinder rookMoveFinder;
    private final BishopMoveFinder bishopMoveFinder;
    private final KnightMoveFinder knightMoveFinder;
    private final PawnMoveFinder pawnMoveFinder;

    public Board() {
        this.moveCounter = 1;
        this.positionPieceMap = new HashMap<>();

        this.kingMoveChecker = new KingMoveChecker(this);
        this.queenMoveChecker = new QueenMoveChecker(this);
        this.rookMoveChecker = new RookMoveChecker(this);
        this.bishopMoveChecker = new BishopMoveChecker(this);
        this.knightMoveChecker = new KnightMoveChecker(this);
        this.pawnMoveChecker = new PawnMoveChecker(this);

        this.kingMoveFinder = new KingMoveFinder(this);
        this.queenMoveFinder = new QueenMoveFinder(this);
        this.rookMoveFinder = new RookMoveFinder(this);
        this.bishopMoveFinder = new BishopMoveFinder(this);
        this.knightMoveFinder = new KnightMoveFinder(this);
        this.pawnMoveFinder = new PawnMoveFinder(this);

        this.setupBoard();
    }

    public static Board createVisualized(Move[] moves) {
        final Board tempBoard = new Board();

        Arrays.stream(moves).forEach(m -> {
            final Piece piece = m.getPiece();
            Piece newPiece = null;

            switch (piece.getName()) {
                case "Pawn":
                    newPiece = new Pawn(null);
                    break;
                case "Knight":
                    newPiece = new Knight(null);
                    break;
                case "Bishop":
                    newPiece = new Bishop(null);
                    break;
                case "Rook":
                    newPiece = new Rook(null);
                    break;
                case "Queen":
                    newPiece = new Queen(null);
                    break;
                case "King":
                    newPiece = new King(null);
                    break;
            }

            if (newPiece != null) {
                tempBoard.setPiece(newPiece, m.getTo());
            }
        });

        return tempBoard;
    }

    public boolean isEmpty(Position position) {
        return positionPieceMap.get(position) == null;
    }

    public boolean check(Piece piece, Move move) {
        if (piece instanceof King) return kingMoveChecker.check((King) piece, move);
        if (piece instanceof Queen) return queenMoveChecker.check((Queen) piece, move);
        if (piece instanceof Rook) return rookMoveChecker.check((Rook) piece, move);
        if (piece instanceof Bishop) return bishopMoveChecker.check((Bishop) piece, move);
        if (piece instanceof Knight) return knightMoveChecker.check((Knight) piece, move);
        if (piece instanceof Pawn) return pawnMoveChecker.check((Pawn) piece, move);
        throw new IllegalArgumentException("Piece is of unknown type");
    }

    public Move[] getPotentialMoves(Piece piece) {
        if (piece instanceof King) return kingMoveFinder.getPotentialMoves((King) piece);
        if (piece instanceof Queen) return queenMoveFinder.getPotentialMoves((Queen) piece);
        if (piece instanceof Rook) return rookMoveFinder.getPotentialMoves((Rook) piece);
        if (piece instanceof Bishop) return bishopMoveFinder.getPotentialMoves((Bishop) piece);
        if (piece instanceof Knight) return knightMoveFinder.getPotentialMoves((Knight) piece);
        if (piece instanceof Pawn) return pawnMoveFinder.getPotentialMoves((Pawn) piece);
        throw new IllegalArgumentException("Piece is of unknown type");
    }

    public void move(Piece piece, Position to) {
        final Position from = getPosition(piece);
        final Move move = new Move(1, piece, from, to, false);

        if (!check(piece, move)) {
            return;
        }

        this.setEmpty(from);
        setPiece(piece, to);


        if (!piece.hasMoved()) {
            piece.setHasMoved(true);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Piece> T setPiece(Piece piece, Position position) {
        positionPieceMap.put(position, piece);
        return (T) piece;
    }

    public void setEmpty(Position position) {
        setPiece(null, position);
    }

    @SuppressWarnings("unchecked")
    public <T extends Piece> T getPiece(Position position) {
        return (T) this.positionPieceMap.get(position);
    }

    public Position getKing(Color c) {
        for (Map.Entry<Position, Piece> entry : positionPieceMap.entrySet()) {
            if (entry.getValue() instanceof King && entry.getValue().getColor().equals(c)) {
                return entry.getKey();
            }
        }
        throw new IllegalStateException("Cannot find King for color " + c.name());
    }

    public Position getPosition(Piece piece) {
        return positionPieceMap.entrySet().stream().filter(e -> e.getValue() != null && e.getValue().equals(piece)).map(Map.Entry::getKey).findFirst().orElseThrow();
    }

    public boolean isInCheck(Color color) {
        final Position kingPosition = getKing(color);

        for (Piece piece : positionPieceMap.values().stream().filter(p -> !p.getColor().equals(color)).collect(Collectors.toList())) {
            if (Arrays.stream(getPotentialMoves(piece)).map(Move::getTo).anyMatch(p -> p.equals(kingPosition))) {
                return true;
            }
        }
        return false;
    }

    public Position[] getPositionsHorizontally(Position from, int endFile) {
        return Arrays.stream(Position.valuesOf(from.getRank())).filter(p -> p.getFileNumber() >= from.getFileNumber() && p.getFileNumber() <= endFile).toArray(Position[]::new);
    }

    public Position[] getPositionsVertically(Position from, int endRank) {
        return Arrays.stream(Position.valuesOf(from.getFile())).filter(p -> p.getRank() >= from.getRank() && p.getRank() <= endRank).toArray(Position[]::new);
    }

    public Position[] getPositionsDiagonally(Position start) {
        final Set<Position> positions = new HashSet<>();

        final PositionFace[] faces = new PositionFace[]{
                PositionFace.NORTH_EAST, PositionFace.NORTH_WEST,
                PositionFace.SOUTH_EAST, PositionFace.SOUTH_WEST
        };

        final int x = start.getFileNumber();
        final int y = start.getRank();

        final int stepsNorth = 8 - y;
        final int stepsSouth = y - 1;

        final int stepsEast = 8 - x;
        final int stepsWest = x - 1;

        final int biggest = max(stepsNorth, stepsSouth, stepsEast, stepsWest);

        for (int i = 0; i < biggest; i++) {
            int finalI = i + 1;
            Arrays.stream(faces).filter(f -> start.isRelative(f, finalI)).map(f -> start.getRelative(f, finalI)).forEach(positions::add);
        }

        return positions.toArray(Position[]::new);
    }

    @SuppressWarnings("unchecked")
    public <T extends Piece> T getObstructionHorizontally(Position from, int endFile) {
        if (!hasObstructionHorizontally(from, endFile)) return null;
        return (T) Arrays.stream(getPositionsHorizontally(from, endFile))
                .filter(p -> p != from)
                .filter(p -> !isEmpty(p)).findAny().map(this::getPiece).orElseThrow();
    }

    @SuppressWarnings("unchecked")
    public <T extends Piece> T getObstructionVertically(Position from, int endFile) {
        if (!hasObstructionVertically(from, endFile)) return null;
        return (T) Arrays.stream(getPositionsVertically(from, endFile))
                .filter(p -> p != from)
                .filter(p -> !isEmpty(p)).findAny().map(this::getPiece).orElseThrow();
    }

    public boolean hasObstructionHorizontally(Position from, int endFile) {
        return hasObstruction0(getPositionsHorizontally(from, endFile));
    }

    public boolean hasObstructionVertically(Position from, int endRank) {
        return hasObstruction0(getPositionsVertically(from, endRank));
    }

    public boolean hasObstructionDiagonally(Position start, Position end) {
        int xChange = end.getFileNumber() - start.getFileNumber();
        int yChange = end.getRank() - start.getRank();

        int steps = Math.abs(start.getRank() - end.getRank());
        int[] direction = getDirection(xChange, yChange);

        for (int i = 0; i < steps; i++) {
            final Position position = Position.valueOf(start.getFileNumber() + direction[0], start.getRank() + direction[1]);
            if (position != start && !isEmpty(position)) return true;
        }
        return false;
    }

    private boolean hasObstruction0(Position[] positions) {
        return Arrays.stream(positions).filter(p -> !isEmpty(p)).count() > 1;
    }

    private int[] getDirection(int diffX, int diffY) {
        int x = diffX >= 0 ? 1 : 0;
        int y = diffY >= 0 ? 1 : 0;

        return new int[]{x, y};
    }

    private int max(int... ints) {
        int current = 0;

        for (int i : ints) {
            current = Math.max(i, current);
        }

        return current;
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
            obj.addProperty("color", "BLACK");

            final String json = Core.getGson().toJson(obj);

            optSession.get().getBasicRemote().sendText(json);
            System.out.println("Sent move (" + position + ", " + piece + ") to " + userID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        print(null);
    }

    public void print(Color which) {
        if (which != null) {
            System.out.println(which + " to move.");
        }

        class Ansi {
            private static final String RED = "\u001B[31m";
            private static final String GREEN = "\u001B[32m";
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

                System.out.print(((getPiece(p).getColor() != null ? (getPiece(p).getColor() == BLACK ? Ansi.BLUE : Ansi.RED) : Ansi.GREEN)) + getPiece(p).getCode() + " " + Ansi.RESET);
            });

            System.out.println("| " + j);
        }

        System.out.println("===================");
        System.out.println("  A B C D E F G H ");
    }

    public int getMoveCounter() {
        return moveCounter;
    }
}
