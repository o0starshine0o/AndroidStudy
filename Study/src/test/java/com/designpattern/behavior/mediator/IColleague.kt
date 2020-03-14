package com.designpattern.behavior.mediator

abstract class IColleague(private val mediator: IMediator) {
    init {
        mediator.register(this.name(), this)
    }

    fun send(message: String, to: String) {
        println("${name()} send message to $to: $message")
        mediator.getColleague(to)?.receiveMessage(message)
    }

    abstract fun receiveMessage(message: String)
    abstract fun name(): String
}