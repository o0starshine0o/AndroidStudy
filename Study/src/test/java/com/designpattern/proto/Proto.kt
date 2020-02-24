package com.designpattern.proto

class Proto : Cloneable {
    init {
        // 代表构造函数或者说是初始化阶段需要大量时间
        Thread.sleep(100)
    }

    @Throws(CloneNotSupportedException::class)// 克隆失败抛出异常
    public override fun clone(): Proto {
        return super.clone() as Proto // 类型强制转换
    }
}