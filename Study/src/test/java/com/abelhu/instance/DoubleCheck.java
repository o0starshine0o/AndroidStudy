package com.abelhu.instance;

public final class DoubleCheck {
    private DoubleCheck() {
    }

    private volatile static DoubleCheck instance = null;

    public static DoubleCheck getInstance() {
        if (instance == null) {
            synchronized (DoubleCheck.class) {
                if (instance == null) instance = new DoubleCheck();
            }
        }
        return instance;
    }
}
