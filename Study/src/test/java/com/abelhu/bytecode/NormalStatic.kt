package com.abelhu.bytecode


class NormalStatic {
    companion object {
        @JvmStatic
        fun testStatic(normal: Normal) {
            normal.test()
            println("fun test changed to kotlin code with static use")
        }
    }
}