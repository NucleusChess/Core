package com.nucleuschess.move.finder;

import com.nucleuschess.board.Board;
import com.nucleuschess.board.Position;
import com.nucleuschess.move.AbstractMoveFinder;
import com.nucleuschess.move.Move;
import com.nucleuschess.piece.Rook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public final class RookMoveFinder extends AbstractMoveFinder<Rook> {

    public RookMoveFinder(Board board) {
        super(board);
    }

    @Override
    public Move[] getAvailableMoves(Rook piece) {
        List<Move> moves = new ArrayList<>();

        final Position from = board.getPosition(piece);

        Arrays.stream(Position.valuesOf(from.getRank())).filter(p -> p != from)
                .map(p -> new Move(board.getMoveCounter() + 1, piece, from, p, false))
                .forEach(moves::add);
        Arrays.stream(Position.valuesOf(from.getFile())).filter(p -> p != from)
                .map(p -> new Move(board.getMoveCounter() + 1, piece, from, p, false))
                .forEach(moves::add);

        return moves.stream().filter(m -> board.check(piece, m)).toArray(Move[]::new);
    }
}
