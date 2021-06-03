package com.nucleuschess.piece;

import com.nucleuschess.Color;
import com.nucleuschess.board.Board;
import com.nucleuschess.board.Position;
import com.nucleuschess.move.Move;
import com.nucleuschess.util.Magic;

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
public final class Knight extends Piece {

    public Knight(Color color, Position position) {
        super(color, position);
    }

    public Knight(Color color) {
        super(color);
    }

    @Override
    public String getCode() {
        return "N";
    }

    @Override
    public boolean check(Board board, Move move) {
        if (isHorizontal(move)) return false;
        if (isVertical(move)) return false;
        if (isDiagonal(move)) return false;

        final int horizontalSteps = Math.abs(move.getHorizontalSteps());
        final int verticalSteps = Math.abs(move.getVerticalSteps());

        if (horizontalSteps > Magic.MAX_KNIGHT_STEPS_SIDEWARDS || horizontalSteps < Magic.MIN_KNIGHT_STEPS_SIDEWARDS)
            return false;
        return verticalSteps <= Magic.MAX_KNIGHT_STEPS_FORWARD && verticalSteps >= Magic.MIN_KNIGHT_STEPS_FORWARD;
    }
}
