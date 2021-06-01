package com.nucleuschess.board;

import com.nucleuschess.Color;
import com.nucleuschess.piece.*;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

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

    private final Set<Position> positions;

    public Board() {
        this.positions = new LinkedHashSet<>();
        this.setupBoard();
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
        return positions;
    }

    public final Position[] getEmptyPositions() {
        return positions.stream().filter(Position::isEmpty).toArray(Position[]::new);
    }

    public boolean isInCheck(Color color) {
        return false; // implement
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
    }

}
