package com.abelhu.androidstudy.hilt.wrapper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.abelhu.androidstudy.hilt.viewholder.HiltHolder

abstract class BaseWrapper<T : ViewBinding>(val context: Context) {
    protected val binding by lazy { createBinding(LayoutInflater.from(context)) }

    val holder by lazy { HiltHolder(this) }

    val root: View
        get() = binding.root

    abstract fun createBinding(inflater: LayoutInflater): T

    abstract fun fillData(any: Any)
}