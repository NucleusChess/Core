package com.nucleuschess.util.observer;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
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
public final class ObservableList<E> extends LinkedList<E> {

    private final Set<Handler<ObservableList<E>>> handlers = new LinkedHashSet<>();

    public ObservableList() {
    }

    public Collection<Handler<ObservableList<E>>> getHandlers() {
        return handlers;
    }

    public void addHandler(Handler<ObservableList<E>> handler) {
        handlers.add(handler);
    }

    public void removeHandler(Handler<ObservableList<E>> handler) {
        handlers.remove(handler);
    }

    private void update() {
        handlers.forEach(h -> h.handle(this));
    }

    @Override
    public boolean add(E e) {
        boolean b = super.add(e);
        this.update();

        return b;
    }

    @Override
    public boolean remove(Object obj) {
        boolean b = super.remove(obj);
        this.update();

        return b;
    }

    @Override
    public void clear() {
        super.clear();
        this.update();
    }
}
