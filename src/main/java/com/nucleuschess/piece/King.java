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
public final class King extends Piece {

    public King(Color color, Position position) {
        super(color, position);
    }

    public King(Color color) {
        super(color);
    }

    @Override
    public String getCode() {
        return "K";
    }

    @Override
    public boolean check(Board board, Move move) {
        if (move.getHorizontalSteps() > Magic.MAX_KING_STEPS_SIDEWARDS) return false;
        if (move.getVerticalSteps() > Magic.MAX_KING_STEPS_FORWARD) return false;
        if (!move.getTo().isEmpty()) return false;

        return true;
    }
}
