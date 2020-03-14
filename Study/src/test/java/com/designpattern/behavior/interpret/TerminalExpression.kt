package com.designpattern.behavior.interpret

class TerminalExpression : IExpression {
    override fun interpret(context: Context) {
        println(context.char.toUpperCase())
    }
}