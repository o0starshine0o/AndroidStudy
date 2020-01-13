package com.visible

import com.abelhu.java.Child
import com.visible.Parent
import org.junit.Test

class VisibleTest {

    @Test
    fun testVisible() {
        val clazz: Parent = Child()
        assert(Child::class.java.simpleName == clazz.name())
    }
}