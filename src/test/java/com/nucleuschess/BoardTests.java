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
        final Position position = Position.valueOf(fileNumber, rank);
        final Piece piece = board.getPiece(position);

        Assertions.assertNotNull(piece);
        Assertions.assertEquals("K", board.getPiece(Position.E1).getCode());
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
                final Position position = Position.valueOf(j, i);
                final String code = board.isEmpty(position) ? "O" : board.getPiece(position).getCode();
                builder.append(code);
            }

            System.out.println("Currently at rank " + i);
            Assertions.assertEquals(expected[i - 1], builder.toString());
        }
    }

    @Test
    @Order(3)
    public void checkFile() {
        final Position[] positions = Position.valuesOf('d');

        Assertions.assertNotNull(positions);
        Assertions.assertEquals(8, positions.length);

        final String expected = "QPOOOOPQ";

        final StringBuilder result = new StringBuilder();
        Arrays.stream(positions).forEach(p -> result.append(board.isEmpty(p) ? "O" : board.getPiece(p).getCode()));

        Assertions.assertEquals(expected, result.toString());
    }

    @Test
    public void checkRank() {
        final Position[] positions = Position.valuesOf(1);

        Assertions.assertNotNull(positions);
        Assertions.assertEquals(positions.length, 8);

        final String expected = "RNBQKBNR";

        final StringBuilder result = new StringBuilder();
        Arrays.stream(positions).forEach(p -> result.append(board.isEmpty(p) ? "O" : board.getPiece(p).getCode()));

        Assertions.assertEquals(expected, result.toString());
    }

    @Test
    @Order(4)
    public void checkPartialFile() {

        final Position[] positions = board.getPositionsVertically('c', 3, 7);
        Assertions.assertNotNull(positions);
        Assertions.assertEquals(5, positions.length);

        final String expected = "OOOOP";
        final StringBuilder result = new StringBuilder();

        Arrays.stream(positions).forEach(p -> result.append(board.isEmpty(p) ? "O" : board.getPiece(p).getCode()));
        Assertions.assertEquals(expected, result.toString());
    }

    @Test
    public void checkPartialRank() {
        final Position[] positions = board.getPositionsHorizontally(8, 3, 8);
        Assertions.assertNotNull(positions);
        Assertions.assertEquals(6, positions.length);

        final String expected = "BQKBNR";
        final StringBuilder result = new StringBuilder();

        Arrays.stream(positions).forEach(p -> result.append(board.isEmpty(p) ? "O" : board.getPiece(p).getCode()));
        Assertions.assertEquals(expected, result.toString());
    }

    @Test
    public void testPawn() {
        final Position from = Position.D2;
        final Pawn piece = board.getPiece(from);
        final Move move = new Move(1, piece, from, Position.D4, false);

        Assertions.assertTrue(board.check(piece, move));
    }

    @Test
    public void testHorizontalRookCollision() {
        final Position from = Position.A1;
        final Rook piece = board.getPiece(from);
        final Move move = new Move(1, piece, from, Position.E1, false);

        Assertions.assertFalse(board.check(piece, move));
    }

    @Test
    public void testVerticalRookCollision() {
        final Position from = Position.A1;
        final Rook piece = board.getPiece(from);
        final Move move = new Move(1, piece, from, Position.A6, false);

        Assertions.assertFalse(board.check(piece, move));
    }

    @Test
    public void testNormalRookMovement() {
        final Position from = Position.A1;
        final Rook piece = board.getPiece(from);
        final Move move = new Move(1, piece, from, Position.A6, false);

        board.setEmpty(Position.A2);

        Assertions.assertTrue(board.isEmpty(Position.A2));
        Assertions.assertTrue(board.check(piece, move));
    }

    @Test
    public void testOpening() {
        final Position from = Position.E2;
        final Pawn pawn = board.getPiece(from);

        final Move move = new Move(1, pawn, from, Position.E4, false);

        Assertions.assertTrue(board.check(pawn, move));
        board.print();
    }

}
