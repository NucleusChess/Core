package com.nucleuschess.board;/*
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

public enum PositionFace {

    SELF(0, 0),
    NORTH(0, 1),
    SOUTH(0, -1),
    WEST(-1, 0),
    EAST(1, 0),
    NORTH_WEST(NORTH, WEST),
    NORTH_EAST(NORTH, EAST),
    SOUTH_WEST(SOUTH, WEST),
    SOUTH_EAST(SOUTH, EAST);

    private final int modX;
    private final int modY;

    PositionFace(int modX, int modY) {
        this.modX = modX;
        this.modY = modY;
    }

    PositionFace(PositionFace a, PositionFace b) {
        this(a.modX + b.modX, a.modY + b.modY);
    }

    /**
     * Get the amount of X-coordinates to modify to get the represented block
     *
     * @return Amount of X-coordinates to modify
     */
    public int getModX() {
        return modX;
    }

    /**
     * Get the amount of Y-coordinates to modify to get the represented block
     *
     * @return Amount of Y-coordinates to modify
     */
    public int getModY() {
        return modY;
    }
}
