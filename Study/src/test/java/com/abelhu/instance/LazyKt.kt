package com.abelhu.instance

class LazyKt private constructor() {
    companion object {
        private var instance: LazyKt? = null
            get() {
                if (field == null) field = LazyKt()
                return field
            }
    }
}