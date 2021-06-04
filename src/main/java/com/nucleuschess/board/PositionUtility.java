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
public final class PositionUtility {

    private static final char[] files = "abcdefgh".toCharArray(); // can later be moved as a local variable if not needed elsewhere

    private PositionUtility() {
    }

    public static char getFile(int i) {
        if (i < 1 || i > 8) {
            throw new IllegalArgumentException("Invalid file specified: " + i);
        }

        return files[i - 1]; // so if we specify file 2 means searching at index 1 in the array
    }

    public static char[] getFiles(int... ints) {
        char[] files = new char[ints.length];

        int index = 0;

        for (int i : ints) {
            files[index] = files[i - 1];
            index++;
        }

        return files;
    }

    public static int getFileNumber(char c) {
        for (int i = 0; i < files.length; i++) {
            if (files[i] == c) {
                return i + 1;
            }
        }
        throw new IllegalArgumentException("Cannot find number for file " + c);
    }


}
