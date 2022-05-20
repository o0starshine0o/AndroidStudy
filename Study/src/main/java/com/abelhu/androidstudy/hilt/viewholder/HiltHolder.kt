package com.abelhu.androidstudy.hilt.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.abelhu.androidstudy.hilt.wrapper.BaseWrapper

// 这个不用关心, 框架里有实现
class HiltHolder<T : ViewBinding>(val wrapper: BaseWrapper<T>) : RecyclerView.ViewHolder(wrapper.root)