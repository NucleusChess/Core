package com.nucleuschess.util.observer;

public final class Observables {

    private Observables() { }

    public static <T> Observable<T> newObservable(T t) {
        return new Observable<>(t);
    }

    public static <T> ObservableList<T> newObservableList() {
        return new ObservableList<>();
    }

    public static <T> ObservableSet<T> newObservableSet() {
        return new ObservableSet<>();
    }

    public static <K, V> ObservableMap<K, V> newObservableMap() {
        return new ObservableMap<>();
    }
}
