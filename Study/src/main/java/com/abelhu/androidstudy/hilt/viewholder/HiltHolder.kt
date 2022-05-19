package com.abelhu.androidstudy.hilt.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.abelhu.androidstudy.hilt.wrapper.BaseWrapper

class HiltHolder<T : ViewBinding>(val wrapper: BaseWrapper<T>) : RecyclerView.ViewHolder(wrapper.root)