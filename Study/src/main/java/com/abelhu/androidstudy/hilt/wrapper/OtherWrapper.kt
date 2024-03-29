package com.abelhu.androidstudy.hilt.wrapper

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.abelhu.androidstudy.databinding.OtherWrapperBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * 和DefaultWrapper不同,这里其实是不需要@Inject注解
 * 因为生成OtherWrapper实例的方法都在 {#link AnyModule} 中
 *
 * @param context 这里是application的预定义限定符,会自动构建
 */
class OtherWrapper @Inject constructor(@ApplicationContext context: Context, private val otherParams: Any) : BaseWrapper<OtherWrapperBinding>(context) {

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup) = OtherWrapperBinding.inflate(inflater, parent, false)

    override fun fillData(data: Any) {
        // 这里处理业务数据填充
        if (data is Int) binding.text.text = "$data: $otherParams"
    }
}