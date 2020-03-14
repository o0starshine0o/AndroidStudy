package com.designpattern.behavior.memento

import org.junit.Test

class Test {
    /**
     * Android的savedInstanceState就是个很好的例子
     */
    @Test
    fun test() {
        // 创建一个组织者
        val organizer = Organizer()
        // 创建一个管理者
        val manager = Manager()
        // 获取目前的状态(备忘录)，并保存
        manager.memento = organizer.createMemento()
        // 修改组织者的状态
        organizer.state = "error"
        // 恢复组织者的状态
        organizer.restoreMemento(manager.memento)
        // 查看
        println(organizer.state)
    }
}