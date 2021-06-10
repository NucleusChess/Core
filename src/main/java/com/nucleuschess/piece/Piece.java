package com.nucleuschess.piece;

import com.nucleuschess.Color;

import java.util.UUID;

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
public abstract class Piece {

    private final UUID uuid;

    private final Color color;
    private boolean hasMoved = false;

    public Piece(Color color) {
        this.uuid = UUID.randomUUID();
        this.color = color;
    }

    public final String getName() {
        return getClass().getSimpleName();
    }

    public abstract String getCode();

    public Color getColor() {
        return color;
    }

    public UUID getId() {
        return uuid;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    public void setHasMoved(boolean moved) {
        if (hasMoved) throw new IllegalStateException("Changing state when already true");

        this.hasMoved = moved;
    }
}
