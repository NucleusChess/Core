package com.nucleuschess.move.finder;

import com.nucleuschess.board.Board;
import com.nucleuschess.board.Position;
import com.nucleuschess.board.PositionFace;
import com.nucleuschess.move.AbstractMoveFinder;
import com.nucleuschess.move.Move;
import com.nucleuschess.piece.King;

import java.util.Arrays;

import static com.nucleuschess.board.PositionFace.*;

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
public final class KingMoveFinder extends AbstractMoveFinder<King> {

    public KingMoveFinder(Board board) {
        super(board);
    }

    @Override
    public Move[] getPotentialMoves(King piece) {
        final Position from = board.getPosition(piece);
        final PositionFace[] faces = new PositionFace[]{
                NORTH, SOUTH, EAST, WEST,
                NORTH_WEST, NORTH_EAST,
                SOUTH_WEST, SOUTH_EAST
        };
        return Arrays.stream(faces).filter(from::isRelative).map(from::getRelative)
                .map(p -> new Move(board.getMoveCounter() + 1, piece, from, p, false)).toArray(Move[]::new);
    }
}
