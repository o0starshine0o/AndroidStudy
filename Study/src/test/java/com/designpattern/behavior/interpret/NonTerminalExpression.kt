package com.designpattern.behavior.interpret

class NonTerminalExpression(vararg val expression: IExpression) : IExpression {
    override fun interpret(context: Context) {
        println(context.char.toLowerCase())
    }
}