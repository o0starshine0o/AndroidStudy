package com.abelhu.androidstudy.hilt.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.abelhu.androidstudy.hilt.viewholder.HiltHolder
import com.abelhu.androidstudy.hilt.wrapper.DefaultWrapper
import com.abelhu.androidstudy.hilt.wrapper.OtherWrapper

class HiltAdapter(val context: Context) : RecyclerView.Adapter<HiltHolder<out ViewBinding>>() {

    // 这个不用关心, 框架里有实现
    override fun getItemCount() = 100

    // 这个不用关心, 框架里有实现
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = createWrapper(viewType).holder

    // 这个不用关心, 框架里有实现
    override fun onBindViewHolder(holder: HiltHolder<out ViewBinding>, position: Int) = holder.wrapper.fillData(Any())

    // 这个需要关心,对应框架中的createWrapper方法
    private fun createWrapper(viewType: Int) = when (viewType) {
        0 -> OtherWrapper(context)
        else -> DefaultWrapper(context)
    }
}