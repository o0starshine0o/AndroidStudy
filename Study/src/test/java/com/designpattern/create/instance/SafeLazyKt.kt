package com.designpattern.create.instance

class SafeLazyKt private constructor() {
    companion object {
        private var instance: SafeLazyKt? = null
            @Synchronized get() {
                if (field == null) field = SafeLazyKt()
                return field
            }
    }
}