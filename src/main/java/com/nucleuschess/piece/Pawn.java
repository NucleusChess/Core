package com.nucleuschess.piece;

import com.nucleuschess.Color;
import com.nucleuschess.board.Board;
import com.nucleuschess.board.Position;
import com.nucleuschess.board.PositionUtility;
import com.nucleuschess.move.Move;

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
public final class Pawn extends Piece {

    public Pawn(Color color, Position position) {
        super(color, position);
    }

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public String getCode() {
        return "P";
    }

    @Override
    public boolean check(Board board, Move move) {
        final Position from = move.getFrom();
        final Position to = move.getTo();

        final int fromFile = PositionUtility.getFileNumber(from.getFile());
        final int toFile = PositionUtility.getFileNumber(to.getFile());

        final int forwardSteps = to.getRank() - from.getRank();
        final int sideSteps = toFile - fromFile;

        // if you are in check and have to deal with this.
        if (board.isInCheck(getColor())) {
            // TODO FIND CHECK-BLOCKING MOVES
            return false;
        }

        // pawns can't move backwards
        if (forwardSteps < 0 || forwardSteps > 2) {
            return false;
        }

        // moves 2 squares or more but already had the first two steps
        if (forwardSteps == 2 && hasMoved()) {
            return false;
        }

        // pawns cannot phase through other pieces
        if (forwardSteps == 2 && !board.getPosition(to.getFile(), toFile - 1).isEmpty()) {
            return false;
        }

        // TODO if pinned (scan other attackers)

        // squares is already occupied
        // TODO fix this will prevent pawns from capturing
        if (!to.isEmpty()) {
            return false;
        }
        // pawns cannot move files more than 1 step
        return sideSteps <= 1 && sideSteps >= -1;
    }
}
