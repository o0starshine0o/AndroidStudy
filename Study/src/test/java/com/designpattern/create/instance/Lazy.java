package com.designpattern.create.instance;

public final class Lazy {
    private Lazy() {
    }

    private static Lazy lazy = null;

    public static Lazy getInstance() {
        if (lazy == null) lazy = new Lazy();
        return lazy;
    }
}
