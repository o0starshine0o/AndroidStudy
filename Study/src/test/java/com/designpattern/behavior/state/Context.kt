package com.designpattern.behavior.state

class Context {
    var state: State? = null
    fun handle() {
        state?.handle(this)
    }
}