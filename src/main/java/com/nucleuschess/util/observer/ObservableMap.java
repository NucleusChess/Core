package com.nucleuschess.util.observer;

import java.util.Collection;
import java.util.LinkedHashMap;
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
public final class ObservableMap<K, V> extends LinkedHashMap<K, V> {

    private final Set<Handler<ObservableMap<K, V>>> handlers = new LinkedHashSet<>();

    public ObservableMap() {
    }

    public Collection<Handler<ObservableMap<K, V>>> getHandlers() {
        return handlers;
    }

    public void addHandler(Handler<ObservableMap<K, V>> handler) {
        handlers.add(handler);
    }

    public void removeHandler(Handler<ObservableMap<K, V>> handler) {
        handlers.remove(handler);
    }

    private void update() {
        handlers.forEach(h -> h.handle(this));
    }

    @Override
    public V put(K key, V value) {
        V v = super.put(key,value);
        this.update();

        return v;
    }

    @Override
    public V remove(Object key) {
        V v = super.remove(key);
        this.update();

        return v;
    }

    @Override
    public void clear() {
        super.clear();
        this.update();
    }


}
