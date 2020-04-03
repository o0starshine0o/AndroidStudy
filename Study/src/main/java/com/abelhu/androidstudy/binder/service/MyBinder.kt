package com.abelhu.androidstudy.binder.service

import android.os.Binder
import android.os.Parcel

/**
 * Binder机制在Android中的实现主要依靠的是Binder类，其实现了IBinder接口
 * IBinder接口：定义了远程操作对象的基本接口，代表了一种跨进程传输的能力
 * 系统会为每个实现了IBinder接口的对象提供跨进程传输能力
 *
 * DESCRIPTOR是Binder描述符，Binder Server和Client之间将通过这个描述符做验证，要想通过验证Binder通信的两端DESCRIPTOR必须相同，
 * 这也是为什么我们在使用AIDL帮助我们生成Binder代码的时候，必须把AIDL放在相同的包名下，
 * 因为SDK会根据包名为我们生成对应的DESCRIPTOR字符串，这里我们手写Binder，只需要保证两端相同就好了，包名字符串不是必须的
 *
 * 以下对关键的几个方法进行分析
 */
class MyBinder : Binder() {

    /**
     * 根据 参数 descriptor 查找相应的IInterface对象（即plus引用）
     * 来自IBinder
     */
    override fun queryLocalInterface(descriptor: String): MyInterface? {
        return super.queryLocalInterface(descriptor) as? MyInterface
    }

    /**
     * 执行Client进程所请求的目标方法（子类需要复写）
     *
     * @param code：Client进程请求方法标识符。即Server进程根据该标识确定所请求的目标方法
     * @param data：目标方法的参数。（Client进程传进来的，此处就是整数a和b）
     * @param reply：目标方法执行后的结果（返回给Client进程）
     * @param flags: 用来区分这个调用是普通调用还是单边调用
     * 普通调用时，Client端线程会阻塞，直到从Server端接收到返回值
     * 单边调用，Client在传出数据后会立即执行下一段代码，此时两端异步执行，单边调用时函数返回值必须为void
     *
     * @return true->正常完成；false->未知的code请求
     *
     * 注意：这里的：参数code，data.enforceInterface(code.toString())和queryLocalInterface(code.toString())可以是不同的值
     */
    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
        return when (code) {
            // 随便定义了一个值
            19920320 -> {
                // 为了两端Binder匹配，需要验证Binder标识, 先验证能否处理
                data.enforceInterface(code.toString())
                // 从Binder对象中的一个Map<String,IInterface>属性中获取保存的IInterface
                val face = queryLocalInterface(code.toString())
                // 按照写入的顺序读出数据
                val a = data.readInt()
                val b = data.readInt()
                // 写入返回的数据
                reply?.writeInt(face?.add(a, b) ?: 0)
                // 写入返回的数据，可以不需要通过查询Binder.map中保存的IInterface
//                reply?.writeInt(a + b)
                true
            }
            else -> super.onTransact(code, data, reply, flags)
        }

    }
}