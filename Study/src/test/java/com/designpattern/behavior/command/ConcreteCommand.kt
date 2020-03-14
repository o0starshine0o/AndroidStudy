package com.designpattern.behavior.command

class ConcreteCommand(private val receiver: Receiver) : ICommand {

    override fun doCommand() {
        println("ConcreteCommand do command")
        receiver.doSomething()
    }
}