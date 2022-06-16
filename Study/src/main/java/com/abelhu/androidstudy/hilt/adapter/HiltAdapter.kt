package com.abelhu.androidstudy.hilt.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.abelhu.androidstudy.hilt.annotation.BindAny0
import com.abelhu.androidstudy.hilt.annotation.BindAny1
import com.abelhu.androidstudy.hilt.viewholder.HiltHolder
import com.abelhu.androidstudy.hilt.wrapper.DefaultWrapper
import com.abelhu.androidstudy.hilt.wrapper.OtherWrapper
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Inject

/**
 * 方式:把各种wrapper通过hilt注入到adapter中
 *
 * @param fragment 在fragment中使用时,会把当前的fragment作为构造参数传进来
 */
class HiltAdapter @Inject constructor(private val fragment: Fragment) : RecyclerView.Adapter<HiltHolder<out ViewBinding>>() {

    companion object {
        const val OtherWrapper0 = 0
        const val OtherWrapper1 = 1
    }

    // adapter是hilt不支持的类, 所以需要EntryPointAccessors的适当静态方法
    // 确保您以参数形式传递的组件和 EntryPointAccessors 静态方法都与 @EntryPoint 接口上的 @InstallIn 注释中的 Android 类匹配
    private val entryPoint by lazy { EntryPointAccessors.fromFragment(fragment, AdapterEntryPoint::class.java) }

    /**
     * 这是注入点
     * 这里提供各种wrapper, 最好是通过注解生成
     * 因为wrapper不是hilt支持的类,所以需要@EntryPoint注释创建入口点
     * 各种wrapper的生命周期按照fragment来
     */
    @EntryPoint
    @InstallIn(FragmentComponent::class)
    interface AdapterEntryPoint {
        // 这里用OtherWrapper做演示为统一类型提供多个绑定
        @BindAny0
        fun otherWrapper0(): OtherWrapper // 返回在module中被@BindAny0注解方法生成的实例

        // 这里用OtherWrapper做演示为统一类型提供多个绑定
        @BindAny1
        fun otherWrapper1(): OtherWrapper // 返回在module中被@BindAny1注解方法生成的实例

        // 这里用DefaultWrapper作为兜底,也是做一个demo
        fun defaultWrapper(): DefaultWrapper
    }

    // 这个不用关心, 框架里有实现
    override fun getItemCount() = 100

    // 这个不用关心, 框架里有实现
    override fun getItemViewType(position: Int) = position % 2

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
        OtherWrapper0 -> entryPoint.otherWrapper0()
        OtherWrapper1 -> entryPoint.otherWrapper1()
        // 这里用DefaultWrapper作为兜底
        else -> entryPoint.defaultWrapper()
    }.setParent(parent)
}