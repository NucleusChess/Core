package com.nucleuschess.move;/*
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

import com.nucleuschess.piece.Piece;

public interface MoveChecker<T extends Piece> {

    boolean check(T piece, Move move);

    default boolean isHorizontal(Move move) {
        return move.getHorizontalSteps() > 0 && move.getVerticalSteps() == 0;
    }

    default boolean isVertical(Move move) {
        return move.getVerticalSteps() > 0 && move.getHorizontalSteps() == 0;
    }

    default boolean isDiagonal(Move move) {
        if (move.getFrom() == move.getTo()) return false;
        if (isHorizontal(move)) return false;
        if (isVertical(move)) return false;

        return Math.abs(move.getHorizontalSteps() / move.getVerticalSteps()) == 1;
    }
}
