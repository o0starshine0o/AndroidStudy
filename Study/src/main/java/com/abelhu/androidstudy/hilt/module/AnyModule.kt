package com.abelhu.androidstudy.hilt.module

import android.content.Context
import com.abelhu.androidstudy.hilt.annotation.BindAny0
import com.abelhu.androidstudy.hilt.annotation.BindAny1
import com.abelhu.androidstudy.hilt.wrapper.OtherWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * 提供Any类型的数据
 */
@Module
@InstallIn(FragmentComponent::class)
object AnyModule {
    /**
     * 被@BindAny0注解的参数会直接得到这个函数的返回值
     */
    @BindAny0
    @Provides
    fun providesOtherParams0(@ApplicationContext context: Context) = OtherWrapper(context, 0)

    /**
     * 被@BindAny1注解的参数会直接得到这个函数的返回值
     */
    @BindAny1
    @Provides
    fun providesOtherParams1(@ApplicationContext context: Context) = OtherWrapper(context, "1")
}