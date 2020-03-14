package com.designpattern.behavior.command

import org.junit.Test

class Test {
    @Test
    fun test() {
        // 准备一个接受者
        val receiver = Receiver()
        // 创建一个命令
        val command: ICommand = ConcreteCommand(receiver)
        // 把命令交给调用者,这个命令可能是1，2，3...
        val invoker = Invoker().apply { this.command = command }
        // 调用者直接调用命令就可以了
        invoker.action()
    }
}