package com.nucleuschess.board;

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

import java.util.Arrays;

/**
 * A class holding the basic information of a square on a chessboard.
 *
 * @author Wouter Kistemaker
 * @since 1.0-SNAPSHOT
 */
public enum Position {

    A1, B1, C1, D1, E1, F1, G1, H1,
    A2, B2, C2, D2, E2, F2, G2, H2,
    A3, B3, C3, D3, E3, F3, G3, H3,
    A4, B4, C4, D4, E4, F4, G4, H4,
    A5, B5, C5, D5, E5, F5, G5, H5,
    A6, B6, C6, D6, E6, F6, G6, H6,
    A7, B7, C7, D7, E7, F7, G7, H7,
    A8, B8, C8, D8, E8, F8, G8, H8;

    private final int fileNumber;
    private final char file;
    private final int rank;


    Position() {
        this.fileNumber = PositionUtility.getFileNumber(name().toLowerCase().charAt(0));
        this.file = name().toLowerCase().charAt(0);
        this.rank = Integer.parseInt(String.valueOf(name().charAt(1)));
    }

    public static Position valueOf(char f, int r) {
        return Arrays.stream(values()).filter(p -> p.getRank() == r && p.name().toLowerCase().charAt(0) == f).findFirst().orElseThrow();
    }

    public static Position valueOf(int f, int r) {
        return Arrays.stream(values()).filter(p -> p.getRank() == r && p.getFileNumber() == f).findFirst().orElseThrow();
    }

    public static Position[] valuesOf(int r) {
        return Arrays.stream(values()).filter(v -> v.name().endsWith(String.valueOf(r))).toArray(Position[]::new);
    }

    public static Position[] valuesOf(char f) {
        return Arrays.stream(values()).filter(v -> v.name().toLowerCase().startsWith(String.valueOf(f))).toArray(Position[]::new);
    }

    public Position getRelative(PositionFace face, int multiplier) {
        return valueOf(fileNumber + multiplier * face.getModX(), rank + multiplier * face.getModY());
    }

    public Position getRelative(PositionFace face) {
        return getRelative(face, 1);
    }

    public boolean isRelative(PositionFace face) {
        int x = fileNumber + face.getModX();
        int y = rank + face.getModY();

        return x >= 1 && x <= 8 && y >= 1 && y <= 8;
    }

    public final int getFileNumber() {
        return fileNumber;
    }

    public final char getFile() {
        return file;
    }

    public final int getRank() {
        return rank;
    }
}
