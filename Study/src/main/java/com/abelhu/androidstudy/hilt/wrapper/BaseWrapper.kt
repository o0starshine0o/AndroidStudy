package com.abelhu.androidstudy.hilt.wrapper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.abelhu.androidstudy.hilt.viewholder.HiltHolder

/**
 * 和框架的wrapper略有不同,这里把ViewBinding耦合进来了
 */
abstract class BaseWrapper<T : ViewBinding>(val context: Context) {
    private lateinit var parent: ViewGroup

    protected val binding by lazy { createBinding(LayoutInflater.from(context), parent) }

    val holder by lazy { HiltHolder(this) }

    val root: View
        get() = binding.root

    fun setParent(parent: ViewGroup): BaseWrapper<T> {
        this.parent = parent
        return this
    }

    abstract fun createBinding(inflater: LayoutInflater, parent: ViewGroup): T

    abstract fun fillData(data: Any)
}