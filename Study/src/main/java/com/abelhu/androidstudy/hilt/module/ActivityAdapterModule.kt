package com.abelhu.androidstudy.hilt.module

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

/**
 * 让 Hilt 以依赖项的形式提供同一类型的不同实现
 *
 * @Module 告知 Hilt 如何提供某些类型的实例
 * @InstallIn 告知 Hilt 每个模块将用在或安装在哪个 Android 类中
 */
@Module
@InstallIn(FragmentComponent::class)
class ActivityAdapterModule {

//    @ApplicationAdapter
//    @Provides
//    fun provideApplicationAdapter(application: Application) = HiltAdapter()
//
//    @ActivityAdapter
//    @Provides
//    fun provideActivityAdapter(activity: Activity) = HiltAdapter()
//
//    @FragmentAdapter
//    @Provides
//    fun provideFragmentAdapter(fragment: Fragment) = HiltAdapter(fragment)

//    @ViewAdapter
//    @Provides
//    fun provideViewAdapter(view: View) = HiltAdapter(view)
}