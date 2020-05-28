package com.abelhu.bytecode

import javassist.ClassPool
import javassist.CodeConverter
import javassist.CtNewMethod
import org.junit.Test

class AopTest {
    companion object {
        const val NORMAL_CLASS = "com.abelhu.bytecode.Normal"
        const val STATIC_CLASS = "com.abelhu.bytecode.NormalStatic"
        const val JAVA_STATIC_CLASS = "com.abelhu.bytecode.NormalStatic"
        const val CLIENT_CLASS = "com.abelhu.bytecode.Client"
        const val ORIGIN_FUN = "test"
        const val REDIRECT_FUN = "testRedirect"
        const val STATIC_FUN = "testStatic"
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

    @Test
    fun testNormalToStatic() {
        val pool = ClassPool(true)
        // 定义普通类
        val normalClass = pool.get(NORMAL_CLASS)
        // 获取普通类的方法
        val normalMethod = normalClass.getDeclaredMethod(ORIGIN_FUN)
        // 获取普通类的重定向方法
        val normalRedirectMethod = normalClass.getDeclaredMethod(REDIRECT_FUN)
        // 定义静态类
        val staticClass = pool.get(JAVA_STATIC_CLASS)
        // 获取静态类的方法
        val staticMethod = staticClass.getDeclaredMethod(STATIC_FUN)
        // 获取调用者
        val clientClass = pool.get(CLIENT_CLASS)
        // 定义代码转换
        val codeConverter = CodeConverter()
//        codeConverter.redirectMethodCall(normalMethod, normalRedirectMethod)
        codeConverter.redirectMethodCallToStatic(normalMethod, staticMethod)
        clientClass.instrument(codeConverter)

        // 创建类实例
        val client = clientClass.toClass().newInstance() as Client
        // 调用normal的方法
        client.test()
    }
}