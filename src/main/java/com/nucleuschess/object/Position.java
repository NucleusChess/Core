package com.nucleuschess.object;

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
 * A class holding the basic information of a square on a chessboard.
 *
 * @since 1.0-SNAPSHOT
 * @author Wouter Kistemaker
 */
public final class Position {

    private final char file;
    private final int rank;
    private final Color color;

    public Position(char file, int rank, Color color) {
        this.file = file;
        this.rank = rank;
        this.color = color;
    }

    public final char getFile() {
        return file;
    }

    public final int getRank() {
        return rank;
    }

    public final Color getColor() {
        return color;
    }
}
