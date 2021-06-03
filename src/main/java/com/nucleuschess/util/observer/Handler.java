package com.nucleuschess.util.observer;

public interface Handler<T> {

    void handle(T t);
}