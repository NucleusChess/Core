package com.nucleuschess.util.observer;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public final class ObservableSet<E> extends LinkedHashSet<E> {

    private final Set<Handler<ObservableSet<E>>> handlers = new LinkedHashSet<>();

    public ObservableSet() {
    }

    public Collection<Handler<ObservableSet<E>>> getHandlers() {
        return handlers;
    }

    public void addHandler(Handler<ObservableSet<E>> handler) {
        handlers.add(handler);
    }

    public void removeHandler(Handler<ObservableSet<E>> handler) {
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
