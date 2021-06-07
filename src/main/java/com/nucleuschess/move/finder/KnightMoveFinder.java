package com.nucleuschess.move.finder;

import com.nucleuschess.board.Board;
import com.nucleuschess.board.Position;
import com.nucleuschess.board.PositionFace;
import com.nucleuschess.move.AbstractMoveFinder;
import com.nucleuschess.move.Move;
import com.nucleuschess.piece.Knight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.nucleuschess.board.PositionFace.*;

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
public final class KnightMoveFinder extends AbstractMoveFinder<Knight> {

    public KnightMoveFinder(Board board) {
        super(board);
    }

    @Override
    public Move[] getAvailableMoves(Knight piece) {
        List<Move> moves = new ArrayList<>();
        final Position from = board.getPosition(piece);
        final PositionFace[] faces = new PositionFace[]{
                NORTH_NORTH_EAST, NORTH_NORTH_WEST,
                NORTH_WEST_WEST, NORTH_EAST_EAST,

                SOUTH_SOUTH_EAST, SOUTH_SOUTH_WEST,
                SOUTH_EAST_EAST, SOUTH_WEST_WEST
        };

        Arrays.stream(faces).filter(from::isRelative).forEach(f -> moves.add(new Move(board.getMoveCounter() + 1, piece, from, from.getRelative(f), false)));
        return moves.stream().filter(m -> board.check(piece, m)).toArray(Move[]::new);
    }
}
