package com.designpattern.structure.flyweight

class Factory {
    private val map = HashMap<String, IFly>()
    fun getFly(name: String): IFly {
        var result = map[name]
        if (result == null) {
            result = Fly(name)
            map[name] = result
        }
        return result
    }
}