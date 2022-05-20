package com.abelhu.androidstudy.hilt.wrapper

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.abelhu.androidstudy.databinding.OtherWrapperBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class OtherWrapper @Inject constructor(@ApplicationContext context: Context) : BaseWrapper<OtherWrapperBinding>(context) {

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup) = OtherWrapperBinding.inflate(inflater, parent, false)

    override fun fillData(data: Any) {
        // 这里处理业务数据填充
        if (data is Int) binding.text.text = "$data"
    }
}