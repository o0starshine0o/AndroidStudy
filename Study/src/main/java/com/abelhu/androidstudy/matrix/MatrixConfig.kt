package com.abelhu.androidstudy.matrix

import com.tencent.mrs.plugin.IDynamicConfig

class MatrixConfig private constructor() : IDynamicConfig {
    companion object {
        val instance by lazy { MatrixConfig() }
    }

    override fun get(key: String?, defStr: String?) = defStr
    override fun get(key: String?, defInt: Int) = defInt
    override fun get(key: String?, defLong: Long) = defLong
    override fun get(key: String?, defBool: Boolean) = defBool
    override fun get(key: String?, defFloat: Float) = defFloat
}