package com.nucleuschess.util.observer;

import java.util.LinkedHashSet;
import java.util.Set;

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
