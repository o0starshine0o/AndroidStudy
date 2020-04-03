package com.abelhu.androidstudy.binder.service

import android.os.IInterface

/**
 * 这个类的主要目的就是限制binder所能提供的内容
 */
class MyInterface : IInterface {
    // 注册一个Client可以使用的方法
    fun add(a: Int, b: Int) = a + b

    // 返回当前接口关联的 Binder 对象
    override fun asBinder() = MyBinder()
}