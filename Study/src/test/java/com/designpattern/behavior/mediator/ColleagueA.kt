package com.designpattern.behavior.mediator

class ColleagueA(metiator: IMediator) : IColleague(metiator) {
    override fun receiveMessage(message: String) {
        println("A receive message: $message")
    }

    override fun name() = "A"
}