package com.designpattern.behavior.memento

class Organizer {
    var state: String? = "state"
    fun createMemento() = Memento(state)
    fun restoreMemento(memento: Memento?) {
        state = memento?.state
    }
}