package com.designpattern.instance;

public final class Hunger {
    private Hunger() {
    }

    private static final Hunger hunger = new Hunger();

    public static Hunger getInstance() {
        return hunger;
    }
}
