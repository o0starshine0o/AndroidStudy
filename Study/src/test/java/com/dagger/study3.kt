//package com.dagger
//
//import dagger.Component
//import dagger.Module
//import dagger.Provides
//import okhttp3.OkHttpClient
//import javax.inject.Inject
//import javax.inject.Singleton
//
//// https://blog.yorek.xyz/android/other/dagger2/
//fun main() {
//    DaggerActivity3().onCreate()
//}
//
///**
// * module 需要被注入到@Component注解中
// */
//@Module
//class OkHttpModule {
//    /**
//     * @provides 必须位于 @Module中
//     * OkHttpClient是第三方库(Retrofit)中的类
//     */
//    @Provides
//    @Singleton
//    fun provideOkHttp() = OkHttpClient()
//}
//
///**
// * 因为OkHttpModule中有方法被@Singleton注解
// * 所有但凡引用到OkHttpModule的Component都需要@Singleton注解
// */
//@Singleton
//@Component(modules = [OkHttpModule::class])
//interface OkHttpComponent {
//    fun inject(activity: DaggerActivity3)
//}
//
//class DaggerActivity3 {
//
//    /**
//     * 注意,这里实现了注入第三方库中类的功能
//     */
//    @Inject
//    lateinit var okHttpClient: OkHttpClient
//
//    init {
//        DaggerOkHttpComponent.builder().build().inject(this)
//    }
//
//    fun onCreate() {
//    }
//}