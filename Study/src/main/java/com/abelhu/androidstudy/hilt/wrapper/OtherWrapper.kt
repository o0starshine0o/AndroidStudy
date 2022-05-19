package com.abelhu.androidstudy.hilt.wrapper

import android.content.Context
import android.view.LayoutInflater
import com.abelhu.androidstudy.databinding.OtherWrapperBinding

class OtherWrapper(context: Context) : BaseWrapper<OtherWrapperBinding>(context) {

    override fun createBinding(inflater: LayoutInflater) = OtherWrapperBinding.inflate(inflater)

    override fun fillData(any: Any) {
        binding.anyView
    }
}