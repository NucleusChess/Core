package com.nucleuschess.util.observer;

import java.util.LinkedHashSet;
import java.util.Set;

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
public final class Observable<T> {

    private final Set<Handler<T>> handlers = new LinkedHashSet<>();
    private T t;

    public Observable(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }

    public void set(T t) {
        this.t = t;
        this.update();
    }

    public void addHandler(Handler<T> handler) {
        handlers.add(handler);
    }

    private void update() {
        handlers.forEach(h -> h.handle(t));
    }
}
