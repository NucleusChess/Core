package com.nucleuschess;

import com.nucleuschess.board.Board;
import com.nucleuschess.board.Position;
import com.nucleuschess.move.Move;
import com.nucleuschess.piece.*;
import com.nucleuschess.util.SimpleDisplayNameGenerator;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.Random;

import static com.nucleuschess.Color.BLACK;
import static com.nucleuschess.Color.WHITE;

/*
  Copyright (C) 2020-2021, Wouter Kistemaker.
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU Affero General  License as published
  by the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Affero General  License for more details.
  You should have received a copy of the GNU Affero General  License
  along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

/**
 * JUnit-IntelliJ IDEA has an oddity where it does not show the display name unless the IntelliJ IDEA test runner is selected.
 * |=> Settings > Build, Execution, Deployment > BuildTools > Gradle > "Run tests using: IntelliJ IDEA"
 */
@DisplayNameGeneration(SimpleDisplayNameGenerator.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoardTests {

    private static Board board;
    private static Random random;

    @BeforeEach
    void createBoard() {
        board = new Board();
    }

    @BeforeAll
    static void initializeRandom() {
        random = new Random();
    }

    @Test
    void checkPieces() {
        final int[] ranks = {1, 2, 7, 8};
        final int rank = ranks[random.nextInt(ranks.length)];

        final int fileNumber = random.nextInt(8) + 1; // since the random returns between 0 and 7 but we want 1 and 8
        final Position position = Position.valueOf(fileNumber, rank);
        final Piece piece = board.getPiece(position);

        Assertions.assertNotNull(piece);
        Assertions.assertEquals("K", board.getPiece(Position.E1).getCode());
    }

    @Test
    void checkBoard() {
        Assertions.assertNotNull(board);
        final String[] expected = {
                "RNBQKBNR",
                "PPPPPPPP",
                "OOOOOOOO",
                "OOOOOOOO",
                "OOOOOOOO",
                "OOOOOOOO",
                "PPPPPPPP",
                "RNBQKBNR"
        };
        StringBuilder builder;
        for (int i = 1; i < 9; i++) {
            builder = new StringBuilder();
            for (int j = 1; j < 9; j++) {
                final Position position = Position.valueOf(j, i);
                final String code = board.isEmpty(position) ? "O" : board.getPiece(position).getCode();
                builder.append(code);
            }

            Assertions.assertEquals(expected[i - 1], builder.toString());
        }
    }

    @Test
    void checkFile() {
        final Position[] positions = Position.valuesOf('d');

        Assertions.assertNotNull(positions);
        Assertions.assertEquals(8, positions.length);

        final String expected = "QPOOOOPQ";

        final StringBuilder result = new StringBuilder();
        Arrays.stream(positions).forEach(p -> result.append(board.isEmpty(p) ? "O" : board.getPiece(p).getCode()));

        Assertions.assertEquals(expected, result.toString());
    }

    @Test
    void checkRank() {
        final Position[] positions = Position.valuesOf(1);

        Assertions.assertNotNull(positions);
        Assertions.assertEquals(positions.length, 8);

        final String expected = "RNBQKBNR";

        final StringBuilder result = new StringBuilder();
        Arrays.stream(positions).forEach(p -> result.append(board.isEmpty(p) ? "O" : board.getPiece(p).getCode()));

        Assertions.assertEquals(expected, result.toString());
    }

    @Test
    void checkPartialFile() {

        final Position[] positions = board.getPositionsVertically(Position.C3, 7);
        Assertions.assertNotNull(positions);
        Assertions.assertEquals(5, positions.length);

        final String expected = "OOOOP";
        final StringBuilder result = new StringBuilder();

        Arrays.stream(positions).forEach(p -> result.append(board.isEmpty(p) ? "O" : board.getPiece(p).getCode()));
        Assertions.assertEquals(expected, result.toString());
    }

    @Test
    void checkPartialRank() {
        final Position[] positions = board.getPositionsHorizontally(Position.C8, 8);
        Assertions.assertNotNull(positions);
        Assertions.assertEquals(6, positions.length);

        final String expected = "BQKBNR";
        final StringBuilder result = new StringBuilder();

        Arrays.stream(positions).forEach(p -> result.append(board.isEmpty(p) ? "O" : board.getPiece(p).getCode()));
        Assertions.assertEquals(expected, result.toString());
    }

    @Test
    void testPawn() {
        final Position from = Position.D2;
        final Pawn piece = board.getPiece(from);
        final Move move = new Move(1, piece, from, Position.D4, false);

        Assertions.assertTrue(board.check(piece, move));
    }

    @Test
    void testHorizontalRookCollision() {
        final Position from = Position.A1;
        final Rook piece = board.getPiece(from);
        final Move move = new Move(1, piece, from, Position.E1, false);

        Assertions.assertFalse(board.check(piece, move));
    }

    @Test
    void testVerticalRookCollision() {
        final Position from = Position.A1;
        final Rook piece = board.getPiece(from);
        final Move move = new Move(1, piece, from, Position.A6, false);

        Assertions.assertFalse(board.check(piece, move));
    }

    @Test
    void testNormalRookMovement() {
        final Position from = Position.A1;
        final Rook piece = board.getPiece(from);
        final Move move = new Move(1, piece, from, Position.A6, false);

        board.setEmpty(Position.A2);

        Assertions.assertTrue(board.isEmpty(Position.A2));
        Assertions.assertTrue(board.check(piece, move));
    }

    @Test
    void testOpening() {
        final Position from = Position.E2;
        final Pawn pawn = board.getPiece(from);

        Assertions.assertTrue(board.check(pawn, new Move(1, pawn, from, Position.E4, false)));

        board.move(pawn, Position.E4);
        board.print();
        // This should now be false after the move was executed (!) because the same move would mean from E4-> E4 which is illegal
        Assertions.assertFalse(board.check(pawn, new Move(1, pawn, from, Position.E4, false)));
    }

    @Test
    void testKingInCheck() {
//        Assertions.assertFalse(board.isInCheck(WHITE));
//        Assertions.assertFalse(board.isInCheck(BLACK));
//
//        final Rook rook = board.setPiece(new Rook(WHITE), Position.E5);
//        Assertions.assertFalse(board.isInCheck(BLACK));
//
//        board.setEmpty(Position.E7);
//
//        final Move[] moves = board.getPotentialMoves(rook);
//        System.out.println("lol the rook has literally " + moves.length);
//        System.out.println(Arrays.toString(Arrays.stream(moves).map(Move::getTo).map(Position::name).toArray(String[]::new)));
//
//        board.print();
//
//        Assertions.assertTrue(board.isInCheck(BLACK));

        Assertions.fail();
    }

    @DisplayName("Move Finder Tests")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @Nested
    class MoveFinderTests {

        @DisplayName("Pawn")
        @Order(1)
        @Test
        void testPawnMoveFinder() {
            final Position from = Position.E2;
            final Pawn pawn = board.getPiece(from);

            Move[] moves = board.getPotentialMoves(pawn);
            Assertions.assertEquals(4, moves.length);

            board.move(pawn, Position.E3);

            moves = board.getPotentialMoves(pawn);
            Board.createVisualized(pawn, board.getPosition(pawn), moves).print(pawn.getColor(), board.getPosition(pawn));

            Assertions.assertEquals(4, moves.length);
        }

        @DisplayName("Knight")
        @Order(2)
        @Test
        void testKnightMoveFinder() {
            final Position from = Position.B1;
            final Knight knight = board.getPiece(from);

            Move[] moves = board.getPotentialMoves(knight);
            Assertions.assertEquals(3, moves.length);

            board.move(knight, Position.C3);

            moves = board.getPotentialMoves(knight);
            Board.createVisualized(knight, board.getPosition(knight), moves).print(knight.getColor(), board.getPosition(knight));

            Assertions.assertEquals(8, moves.length);
        }

        @DisplayName("Bishop")
        @Order(3)
        @Test
        void testBishopMoveFinder() {

            final Position from = Position.E4;
            final Bishop bishop = board.setPiece(new Bishop(WHITE), from);

            Move[] moves = board.getPotentialMoves(bishop);
            Board.createVisualized(bishop, board.getPosition(bishop), moves).print(bishop.getColor(), board.getPosition(bishop));

            Assertions.assertEquals(13, moves.length, "The moves found are " +
                    Arrays.toString(Arrays.stream(moves).map(Move::getTo).map(Position::name).toArray(String[]::new)));
        }

        @DisplayName("Rook")
        @Order(4)
        @Test
        void testRookMoveFinder() {
            final Position from = Position.A1;
            final Rook rook = board.getPiece(from);

            Move[] moves = board.getPotentialMoves(rook);
            Assertions.assertEquals(14, moves.length, "The moves found are " +
                    Arrays.toString(Arrays.stream(moves).map(Move::getTo).map(Position::name).toArray(String[]::new)));

            board.setEmpty(Position.A2);

            moves = board.getPotentialMoves(rook);
            Board.createVisualized(rook, board.getPosition(rook), moves).print(rook.getColor(), board.getPosition(rook));

            Assertions.assertEquals(14, moves.length);
        }

        @DisplayName("Queen")
        @Order(5)
        @Test
        void testQueenMoveFinder() {

            final Position from = Position.C5;
            final Queen queen = board.setPiece(new Queen(WHITE), from);

            Move[] moves = board.getPotentialMoves(queen);
            Board.createVisualized(queen, board.getPosition(queen), moves).print(queen.getColor(), board.getPosition(queen));

            Assertions.assertEquals(25, moves.length, "The moves found are " +
                    Arrays.toString(Arrays.stream(moves).map(Move::getTo).map(Position::name).toArray(String[]::new)));
        }

        @DisplayName("King")
        @Order(6)
        @Test
        void testKingMoveFinder() {

            final Position from = Position.E1;
            final King king = board.setPiece(new King(WHITE), from);

            Move[] moves = board.getPotentialMoves(king);
            Board.createVisualized(king, board.getPosition(king), moves).print(king.getColor(), board.getPosition(king));

            Assertions.assertEquals(5, moves.length, "The moves found are " +
                    Arrays.toString(Arrays.stream(moves).map(Move::getTo).map(Position::name).toArray(String[]::new)));
        }
    }
}
