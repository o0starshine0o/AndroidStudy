package com.abelhu.androidstudy.hilt.wrapper

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.abelhu.androidstudy.databinding.DefaultWrapperBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultWrapper @Inject constructor(@ApplicationContext context: Context) : BaseWrapper<DefaultWrapperBinding>(context) {

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup) = DefaultWrapperBinding.inflate(inflater, parent, false)

    override fun fillData(data: Any) {
        // 这里处理业务数据填充
        if (data is Int) binding.text.text = "$data"
    }
}