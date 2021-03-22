package com.designpattern.create.instance

class EnumKt private constructor() {
    companion object {
        fun getInstance() = Enum.Instance.getInstance()
    }

    enum class Enum {
        Instance;

        private val instance: EnumKt = EnumKt()

        fun getInstance() = instance
    }
}