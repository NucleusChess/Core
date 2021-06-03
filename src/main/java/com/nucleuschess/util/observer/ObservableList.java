package com.nucleuschess.util.observer;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

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
