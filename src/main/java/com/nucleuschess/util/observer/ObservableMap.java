package com.nucleuschess.util.observer;

import java.util.*;

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
