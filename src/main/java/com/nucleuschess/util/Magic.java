package com.nucleuschess.util;

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
 * Class containing the magic constants used in this repository
 */
public interface Magic {

    int MIN_PAWN_STEPS_FORWARD = 1;
    int MAX_PAWN_STEPS_FORWARD = 2;
    int MAX_PAWN_STEPS_SIDEWARDS = 1;

    int MAX_KING_STEPS_FORWARD = 1;
    int MAX_KING_STEPS_SIDEWARDS = 1;

    int MAX_KNIGHT_STEPS_FORWARD = 2;
    int MAX_KNIGHT_STEPS_SIDEWARDS = 2;
    int MIN_KNIGHT_STEPS_FORWARD = 1;
    int MIN_KNIGHT_STEPS_SIDEWARDS = 1;

}
