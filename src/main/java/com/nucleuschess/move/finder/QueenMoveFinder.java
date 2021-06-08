package com.nucleuschess.move.finder;

import com.nucleuschess.board.Board;
import com.nucleuschess.board.Position;
import com.nucleuschess.move.AbstractMoveFinder;
import com.nucleuschess.move.Move;
import com.nucleuschess.piece.Queen;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
public final class QueenMoveFinder extends AbstractMoveFinder<Queen> {

    public QueenMoveFinder(Board board) {
        super(board);
    }

    @Override
    public Move[] getPotentialMoves(Queen piece) {
        final Position from = board.getPosition(piece);
        final Position[] horizontal = Position.valuesOf(from.getRank());
        final Position[] vertical = Position.valuesOf(from.getFile());
        final Position[] diagonal = board.getPositionsDiagonally(from);

        Set<Position> positions = new HashSet<>();
        positions.addAll(Arrays.asList(horizontal));
        positions.addAll(Arrays.asList(vertical));
        positions.addAll(Arrays.asList(diagonal));
        positions.remove(from);

        return positions.stream().map(p -> new Move(board.getMoveCounter() + 1, piece, from, p, false)).toArray(Move[]::new);
    }
}
