package com.designpattern.instance;

public final class InnerClass {
    public static Instance getInstance() {
        return Instance.instance;
    }

    private final static class Instance {
        private static final Instance instance = new Instance();
    }
}
