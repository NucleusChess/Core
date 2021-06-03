package com.nucleuschess.piece;

import com.nucleuschess.Color;
import com.nucleuschess.board.Board;
import com.nucleuschess.board.Position;
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

/**
 * Class representing the Queen, which can move horizontally and vertically and diagonally
 *
 * @author Wouter Kistemaker
 * @since 1.0-SNAPSHOT
 */
public final class Queen extends Piece {

    public Queen(Color color, Position position) {
        super(color, position);
    }

    public Queen(Color color) {
        super(color);
    }

    @Override
    public String getCode() {
        return "Q";
    }

    @Override
    public boolean check(Board board, Move move) {

        if (isHorizontal(move) && board.hasObstructionHorizontally(move.getFrom().getRank(), move.getFrom().getFileNumber(), move.getTo().getFileNumber())) {
            return false;
        }

        if (isVertical(move) && board.hasObstructionVertically(move.getFrom().getFileNumber(), move.getFrom().getRank(), move.getTo().getRank())) {
            return false;
        }
        return !isDiagonal(move) || !board.hasObstructionDiagonally(move.getFrom(), move.getTo());
    }
}
