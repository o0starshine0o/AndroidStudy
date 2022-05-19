package com.abelhu.androidstudy.hilt.wrapper

import android.content.Context
import android.view.LayoutInflater
import com.abelhu.androidstudy.databinding.DefaultWrapperBinding

class DefaultWrapper(context: Context) : BaseWrapper<DefaultWrapperBinding>(context) {

    override fun createBinding(inflater: LayoutInflater) = DefaultWrapperBinding.inflate(inflater)

    override fun fillData(any: Any) {
        // 这里处理业务数据填充
        binding.anyView
    }
}