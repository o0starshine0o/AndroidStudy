package com.abelhu.instance;

public final class SafeLazy {
    private SafeLazy() {
    }

    private static SafeLazy lazy = null;

    public static synchronized SafeLazy getInstance() {
        if (lazy == null) lazy = new SafeLazy();
        return lazy;
    }
}
