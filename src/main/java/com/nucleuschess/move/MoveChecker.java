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

import com.nucleuschess.board.Board;
import com.nucleuschess.board.PositionUtility;
import com.nucleuschess.piece.Knight;

public interface MoveChecker {

    boolean check(Board board, Move move);

    default boolean isHorizontal(Move move) {
        final int fromFile = PositionUtility.getFileNumber(move.getFrom().getFile());
        final int toFile = PositionUtility.getFileNumber(move.getTo().getFile());
        return move.getFrom().getRank() == move.getTo().getRank() && fromFile != toFile;
    }

    default boolean isVertical(Move move) {
        if (isHorizontal(move)) return false;
        return move.getTo().getRank() != move.getFrom().getRank();
    }

    default boolean isDiagonal(Move move) {
        if (isHorizontal(move)) return false;
        if (isVertical(move)) return false;

        final int fromFile = PositionUtility.getFileNumber(move.getFrom().getFile());
        final int toFile = PositionUtility.getFileNumber(move.getTo().getFile());
        final int horizontalSteps = Math.abs(toFile - fromFile);
        final int verticalSteps = Math.abs(move.getTo().getRank() - move.getFrom().getRank());

        return (fromFile / toFile == 1 && horizontalSteps / verticalSteps == 1);
    }
}
