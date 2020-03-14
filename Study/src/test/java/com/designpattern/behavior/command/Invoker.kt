package com.designpattern.behavior.command

class Invoker {
    var command: ICommand? = null

    fun action() {
        command?.doCommand()
    }
}