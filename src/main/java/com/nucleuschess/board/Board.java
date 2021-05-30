package com.nucleuschess.board;

import com.nucleuschess.Color;

import java.util.HashSet;
import java.util.Set;

import static com.nucleuschess.Color.WHITE;

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
 * Class representing the Chess-board, with all the squares gathered in a {@link Set<Position>}
 *
 * @since 1.0-SNAPSHOT
 * @author Wouter Kistemaker
 */
public final class Board {

    private final char[] files = "abcdefgh".toCharArray(); // can later be moved as a local variable if not needed elsewhere
    private final Set<Position> positions;

    public Board(){
        this.positions = new HashSet<>();
        this.setupBoard();
    }

    public final Position getPosition(char file, int rank){
        return positions.stream().filter(p-> p.getFile() == file && p.getRank() == rank).findFirst().orElseThrow(NullPointerException::new);
    }

    public final Position[] getPositions(char file){
        return positions.stream().filter(p->p.getFile()==file).toArray(Position[]::new);
    }

    public final Position[] getPositions(int rank){
        return positions.stream().filter(p->p.getRank()==rank).toArray(Position[]::new);
    }

    public final Set<Position> getPositions() {
        return positions;
    }

    private void setupBoard(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Color color = ( i % 2 == 0) ? (j % 2 == 0 ? Color.BLACK : WHITE) : (j % 2 == 0 ? WHITE : Color.BLACK);
                this.positions.add(new Position(files[i], j+1, color));
            }
        }
        // TODO setup board with the standard piece positions.
    }

}
