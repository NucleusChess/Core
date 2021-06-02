package com.nucleuschess;

import com.nucleuschess.board.Board;
import com.nucleuschess.board.Position;
import com.nucleuschess.move.Move;
import com.nucleuschess.piece.Pawn;
import com.nucleuschess.piece.Piece;
import com.nucleuschess.piece.Rook;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.Random;

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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BoardTests {

    private static Board board;
    private static Random random;

    @BeforeAll
    public static void createBoard() {
        board = new Board();
    }

    @BeforeAll
    public static void initializeRandom() {
        random = new Random();
    }

    @Test
    @Order(1)
    public void checkPieces() {
        final int[] ranks = {1, 2, 7, 8};
        final int rank = ranks[random.nextInt(ranks.length)];

        final int fileNumber = random.nextInt(8) + 1; // since the random returns between 0 and 7 but we want 1 and 8
        final Position position = board.getPosition(fileNumber, rank);
        final Piece piece = position.getPiece();

        Assertions.assertNotNull(piece);
        Assertions.assertEquals("K", board.getPosition(5, 1).getPiece().getCode());
    }

    @Test
    @Order(2)
    public void checkBoard() {
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
                final Position position = board.getPosition(j, i);
                final String code = position.isEmpty() ? "O" : position.getPiece().getCode();
                builder.append(code);
            }
            Assertions.assertEquals(expected[i - 1], builder.toString());
        }
    }

    @Test
    public void checkFile() {
        final Position[] positions = board.getPositions('d');

        Assertions.assertNotNull(positions);
        Assertions.assertEquals(positions.length, 8);

        final String expected = "QPOOOOPQ";

        final StringBuilder result = new StringBuilder();
        Arrays.stream(positions).forEach(p -> result.append(p.isEmpty() ? "O" : p.getPiece().getCode()));

        Assertions.assertEquals(expected, result.toString());
    }

    @Test
    public void checkRank() {
        final Position[] positions = board.getPositions(1);

        Assertions.assertNotNull(positions);
        Assertions.assertEquals(positions.length, 8);

        final String expected = "RNBQKBNR";

        final StringBuilder result = new StringBuilder();
        Arrays.stream(positions).forEach(p -> result.append(p.isEmpty() ? "O" : p.getPiece().getCode()));

        Assertions.assertEquals(expected, result.toString());
    }

    @Test
    public void checkPartialFile() {

        final Position[] positions = board.getPositionsVertically(3, 3, 7);
        Assertions.assertNotNull(positions);
        Assertions.assertEquals(5, positions.length);

        final String expected = "OOOOP";
        final StringBuilder result = new StringBuilder();

        Arrays.stream(positions).forEach(p -> result.append(p.isEmpty() ? "O" : p.getPiece().getCode()));
        Assertions.assertEquals(expected, result.toString());
    }

    @Test
    public void checkPartialRank() {
        final Position[] positions = board.getPositionsHorizontally(8, 3, 8);
        Assertions.assertNotNull(positions);
        Assertions.assertEquals(6, positions.length);

        final String expected = "BQKBNR";
        final StringBuilder result = new StringBuilder();

        Arrays.stream(positions).forEach(p -> result.append(p.isEmpty() ? "O" : p.getPiece().getCode()));
        Assertions.assertEquals(expected, result.toString());
    }

    @Test
    public void testPawn() {
        final Position from = board.getPosition(4, 2); // D4
        final Pawn piece = from.getPiece();
        final Move move = new Move(1, piece, from, board.getPosition(4, 4), false);

        Assertions.assertTrue(piece.check(board, move));
    }

    @Test
    public void testHorizontalRookCollision() {
        final Position from = board.getPosition(1, 1); // A1
        final Rook piece = from.getPiece();
        final Move move = new Move(1, piece, from, board.getPosition(5, 1), false);

        Assertions.assertTrue(piece.check(board, move));
    }

    @Test
    public void testVerticalRookCollision() {
        final Position from = board.getPosition(1, 1); // A1
        final Rook piece = from.getPiece();
        final Move move = new Move(1, piece, from, board.getPosition(1, 6), false);

        Assertions.assertTrue(piece.check(board, move));
    }

    @Test
    public void testNormalRookMovement() {
        final Position from = board.getPosition(1, 1); // A1
        final Rook piece = from.getPiece();
        final Move move = new Move(1, piece, from, board.getPosition(1, 6), false);

        board.getPosition(1, 2).setEmpty();

        Assertions.assertTrue(piece.check(board, move));
    }
}
