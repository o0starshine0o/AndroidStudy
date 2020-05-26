package com.abelhu.bytecode

import javassist.ClassPool
import javassist.CtNewMethod
import org.junit.Test

class AopTestTime {
    companion object {
        const val NORMAL_CLASS = "com.abelhu.bytecode.Normal"
        const val ORIGIN_FUN = "test"
    }

    @Test
    fun testNormalTime() {
        val pool = ClassPool(true)
        // 定义类
        val normalClass = pool.get(NORMAL_CLASS)
        // 需要修改的方法
        val method = normalClass.getDeclaredMethod(ORIGIN_FUN)
        // 修改原有的方法
        method.name = "$ORIGIN_FUN\$agent"
        // 创建新的方法，复制原来的额方法
        val newMethod = CtNewMethod.copy(method, ORIGIN_FUN, normalClass, null)
        // 注入代码
        val body = StringBuffer()
        // 记录时间
        body.append("{\n")
        body.append("long start = System.currentTimeMillis();\n")
        // 调用原先的方法
        body.append("$ORIGIN_FUN\$agent($$);\n")
        // 测定执行时间
        body.append("System.out.println(\"function $ORIGIN_FUN() take: \" + (System.currentTimeMillis() - start) + \"ms\");")
        body.append("}")
        // 替换新方法的逻辑
        newMethod.setBody(body.toString())
        // 增加新方法
        normalClass.addMethod(newMethod)
        // 创建类实例
        val normal = normalClass.toClass().newInstance() as Normal
        // 调用normal的方法
        normal.test()
    }
}