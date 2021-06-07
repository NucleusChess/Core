package com.nucleuschess.move.checker;

import com.nucleuschess.board.Board;
import com.nucleuschess.board.Position;
import com.nucleuschess.move.AbstractMoveChecker;
import com.nucleuschess.move.Move;
import com.nucleuschess.piece.Pawn;
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
public final class PawnMoveChecker extends AbstractMoveChecker<Pawn> {

    public PawnMoveChecker(Board board) {
        super(board);
    }

    @Override
    public boolean check(Pawn pawn, Move move) {
        final Position from = move.getFrom();
        final Position to = move.getTo();

        final int fromFile = from.getFileNumber();
        final int toFile = to.getFileNumber();

        final int forwardSteps = to.getRank() - from.getRank();
        final int sideSteps = toFile - fromFile;

        // if you are in check and have to deal with this.
//        if (board.isInCheck(pawn.getColor())) {
//             TODO FIND CHECK-BLOCKING MOVES
//            return false;
//        }

        if (Math.abs(sideSteps) > Magic.MAX_PAWN_STEPS_SIDEWARDS) return false;

        if (forwardSteps == 2) {
            if (pawn.hasMoved()) return false;
            return board.isEmpty(Position.valueOf(to.getFileNumber(), to.getRank() - 1));
        }
        // pawns can't move backwards
        if (forwardSteps < Magic.MIN_PAWN_STEPS_FORWARD || forwardSteps > Magic.MAX_PAWN_STEPS_FORWARD) {
            return false;
        }

        // TODO if pinned (scan other attackers)

        // squares is already occupied
        // TODO fix this will prevent pawns from capturing
        return board.isEmpty(to);
    }
}
