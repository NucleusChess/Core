package com.nucleuschess.move.checker;

import com.nucleuschess.board.Board;
import com.nucleuschess.move.AbstractMoveChecker;
import com.nucleuschess.move.Move;
import com.nucleuschess.piece.Knight;
import com.nucleuschess.util.Magic;

/*
  Copyright (C) 2021, NucleusChess.
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
public final class KnightMoveChecker extends AbstractMoveChecker<Knight> {

    public KnightMoveChecker(Board board) {
        super(board);
    }

    @Override
    public boolean check(Knight piece, Move move) {
        if (isHorizontal(move)) return false;
        if (isVertical(move)) return false;
        if (isDiagonal(move)) return false;

        final int horizontalSteps = Math.abs(move.getHorizontalSteps());
        final int verticalSteps = Math.abs(move.getVerticalSteps());

        // Can't capture own pieces
        if (!board.isEmpty(move.getTo()) && board.getPiece(move.getTo()).getColor() == piece.getColor()) {
            return false;
        }

        if (horizontalSteps > Magic.MAX_KNIGHT_STEPS_SIDEWARDS || horizontalSteps < Magic.MIN_KNIGHT_STEPS_SIDEWARDS)
            return false;
        return verticalSteps <= Magic.MAX_KNIGHT_STEPS_FORWARD && verticalSteps >= Magic.MIN_KNIGHT_STEPS_FORWARD;
    }
}
