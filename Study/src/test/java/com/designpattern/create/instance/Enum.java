package com.designpattern.create.instance;

public class Enum {

    private Enum() {
    }

    static Enum getInstance() {
        return InnerEnum.Instance.getInstance();
    }

    enum InnerEnum {
        Instance;

        private final Enum data;

        InnerEnum() {
            data = new Enum();
        }

        Enum getInstance() {
            return data;
        }
    }

}
