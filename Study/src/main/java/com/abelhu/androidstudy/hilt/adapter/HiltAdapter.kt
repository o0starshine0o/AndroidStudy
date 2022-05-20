package com.abelhu.androidstudy.hilt.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.abelhu.androidstudy.hilt.viewholder.HiltHolder
import com.abelhu.androidstudy.hilt.wrapper.DefaultWrapper
import com.abelhu.androidstudy.hilt.wrapper.OtherWrapper
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.FragmentComponent

/**
 * 方式:把各种wrapper通过hilt注入到adapter中
 */
class HiltAdapter(private val fragment: Fragment) : RecyclerView.Adapter<HiltHolder<out ViewBinding>>() {

    companion object {
        const val OtherWrapper = 0
    }

    // adapter是hilt不支持的类, 所以需要EntryPointAccessors的适当静态方法
    // 确保您以参数形式传递的组件和 EntryPointAccessors 静态方法都与 @EntryPoint 接口上的 @InstallIn 注释中的 Android 类匹配
    private val entryPoint by lazy { EntryPointAccessors.fromFragment(fragment, AdapterEntryPoint::class.java) }

    /**
     * 这是注入点
     * 这里提供各种wrapper, 最好是通过注解生成
     * 各种wrapper的生命周期按照fragment来
     */
    @EntryPoint
    @InstallIn(FragmentComponent::class)
    interface AdapterEntryPoint {
        // 这里用OtherWrapper做一个demo,实际可以有很多种wrapper
        fun otherWrapper(): OtherWrapper

        // 这里用DefaultWrapper作为兜底
        fun defaultWrapper(): DefaultWrapper
    }

    // 这个不用关心, 框架里有实现
    override fun getItemCount() = 100

    // 这个不用关心, 框架里有实现
    override fun getItemViewType(position: Int) = OtherWrapper

    // 这个不用关心, 框架里有实现
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = createWrapper(parent, viewType).holder

    // 这个不用关心, 框架里有实现
    override fun onBindViewHolder(holder: HiltHolder<out ViewBinding>, position: Int) = holder.wrapper.fillData(position)

    /**
     * 这里提供各种wrapper的映射关系, 最好通过注解生成
     *
     * 最后一行,parent没法通过hilt注入,只能通过调用方法的方式注入
     */
    private fun createWrapper(parent: ViewGroup, viewType: Int) = when (viewType) {
        // 这里用OtherWrapper做一个demo,实际可以有很多种wrapper
        OtherWrapper -> entryPoint.otherWrapper()
        // 这里用DefaultWrapper作为兜底
        else -> entryPoint.defaultWrapper()
    }.setParent(parent)
}