package com.nucleuschess.move.checker;

import com.nucleuschess.board.Board;
import com.nucleuschess.move.AbstractMoveChecker;
import com.nucleuschess.move.Move;
import com.nucleuschess.piece.Rook;

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
public final class RookMoveChecker extends AbstractMoveChecker<Rook> {

    public RookMoveChecker(Board board) {
        super(board);
    }

    @Override
    public boolean check(Rook piece, Move move) {
        // rooks can only move horizontally and vertically
        if (!isHorizontal(move) && !isVertical(move)) return false;
        if (isHorizontal(move) && board.hasObstructionHorizontally(move.getFrom().getRank(), move.getFrom().getFileNumber(), move.getTo().getFileNumber())) {
            return false;
        }

        return !isVertical(move) || !board.hasObstructionVertically(move.getFrom().getFile(), move.getFrom().getRank(), move.getTo().getRank());
    }
}
