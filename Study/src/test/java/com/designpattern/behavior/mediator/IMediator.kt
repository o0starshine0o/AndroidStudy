package com.designpattern.behavior.mediator

abstract class IMediator {
    private val map = HashMap<String, IColleague>()

    fun getColleague(name: String) = map[name]

    fun register(name: String, colleague: IColleague) {
        map[name] = colleague
    }
}