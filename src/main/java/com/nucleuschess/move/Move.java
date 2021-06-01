package com.nucleuschess.move;

import com.nucleuschess.Color;
import com.nucleuschess.board.Position;
import com.nucleuschess.board.PositionUtility;
import com.nucleuschess.piece.Piece;

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
public final class Move {

    private final int number;
    private final Color color;
    private final Piece piece;
    private final Position from;
    private final Position to;
    private final boolean isCapture;

    public Move(int number, Piece piece, Position from, Position to, boolean isCapture) {
        this.number = number;
        this.piece = piece;
        this.color = piece.getColor();
        this.from = from;
        this.to = to;
        this.isCapture = isCapture;
    }

    public int getNumber() {
        return number;
    }

    public Color getColor() {
        return color;
    }

    public Piece getPiece() {
        return piece;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    public int getHorizontalSteps() {
        return Math.abs(from.getRank() - to.getRank());
    }

    public int getVerticalSteps() {
        return Math.abs(PositionUtility.getFileNumber(from.getFile()) - PositionUtility.getFileNumber(to.getFile()));
    }

    public boolean isCapture() {
        return isCapture;
    }
}
