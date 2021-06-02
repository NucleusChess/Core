package com.nucleuschess.util;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public final class ObservableSet<E> extends LinkedList<E> {

    private final Set<Handler<E>> handlers = new LinkedHashSet<>();

    public ObservableSet() {
    }

    public void addHandler(Handler<E> handler) {
        handlers.add(handler);
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

    private void update() {
        handlers.forEach(h -> h.handle(this));
    }

    public interface Handler<E> {
        void handle(ObservableSet<E> set);
    }
}
