package com.abelhu.java

import com.visible.Parent

class Child : Parent() {
    fun name() = javaClass.simpleName
}