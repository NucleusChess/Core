package com.nucleuschess.board;

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

import com.nucleuschess.Color;
import com.nucleuschess.piece.Piece;

/**
 * A class holding the basic information of a square on a chessboard.
 *
 * @author Wouter Kistemaker
 * @since 1.0-SNAPSHOT
 */
public final class Position {

    private final char file;
    private final int rank;
    private final Color color;

    private Piece piece;

    public Position(char file, int rank, Color color) {
        this.file = file;
        this.rank = rank;
        this.color = color;
    }

    public final char getFile() {
        return file;
    }

    public final int getFileNumber() {
        return PositionUtility.getFileNumber(file);
    }

    public final int getRank() {
        return rank;
    }

    public final String getCode() {
        return rank + "" + file;
    }

    public final Color getColor() {
        return color;
    }

    @SuppressWarnings("unchecked")
    public final <T extends Piece> T getPiece() {
        return (T) piece;
    }

    public final void setPiece(Piece piece) {
        this.piece = piece;

        if (piece != null) {
            piece.setPosition(this);
        }
    }

    public final void setEmpty() {
        setPiece(null);
    }

    public boolean isEmpty() {
        return piece == null;
    }
}
