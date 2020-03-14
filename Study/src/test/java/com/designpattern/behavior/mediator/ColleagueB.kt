package com.designpattern.behavior.mediator

class ColleagueB(metiator: IMediator) : IColleague(metiator) {
    override fun receiveMessage(message: String) {
        println("B receive message: $message")
    }

    override fun name() = "B"
}