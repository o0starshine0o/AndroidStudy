//package com.dagger
//
//import dagger.*
//import javax.inject.Inject
//import javax.inject.Provider
//import javax.inject.Qualifier
//import javax.inject.Scope
//
//class Man {
//
//    // 用于判断car0是否等于car1
//    @Inject
//    @Type
//    lateinit var car0: Car
//
//    // 用于判断car0是否等于car1
//    @Inject
//    @Type
//    lateinit var car1: Car
//
//    @Inject
//    @Type
//    lateinit var car2: Lazy<Car>
//
//    @Inject
//    @Type
//    lateinit var car3: Lazy<Car>
//
//    @Inject
//    @Type("Blue")
//    lateinit var car: Lazy<Car>
//
//    @Inject
//    @Type("Red")
//    lateinit var cars: Provider<Car>
//
//    init {
//        DaggerManComponent.create().injectMan(this)
//        // 等同
////        DaggerManComponent.create().getInjector().injectMembers(this)
//        // 等同，注意无参的Module是可以省略的，就像CarModule
//        // 大型项目中会有很多的Module
////        DaggerManComponent.builder().carModule().build().injectMan(this)
//    }
//}
//
///**
// * 表明：
// * 1、Car的实例，可以通过这个构造函数进行构造
// * 2、Car类型的变量可以通过Dagger进行注入
// *
// * 这个注解（@Singleton）表明，在规定的生命周期内会使用同一个对象，特别注意是在规定的生命周期内单例，并不是全局单例，或者可以理解为在@Component内单例
// */
////@Singleton
//data class Car @Inject constructor(val name: String)
//
///**
// * 为Component提供依赖，相当于告诉Component，如果需要依赖可以来找我
// * Module可以通过includes依赖其他的Module eg:@Module(includes = [ContextModule::class])
// */
//@Module
//class CarModule {
//    @Provides
//    // 注意@Type是@Qualifier的一个实现，因为这2个provide都返回Car实例，如果没有@Qualifier来区分，就不知道具体调用哪个provide了
//    @Type
//    fun provideDefaultCar() = Car("default car fff")
//
//    @Provides
//    // 注意@Type是@Qualifier的一个实现，因为这2个provide都返回Car实例，如果没有@Qualifier来区分，就不知道具体调用哪个provide了
//    @Type("Blue")
//    fun provideBlueCar() = Car("blue car")
//
//    @Provides
//    // 注意@Type是@Qualifier的一个实现，因为这2个provide都返回Car实例，如果没有@Qualifier来区分，就不知道具体调用哪个provide了
//    @Type("Red")
//    fun provideRedCar() = Car("red car")
//}
//
///**
// * 桥梁：把依赖类连接到目标类
// * 编译后会生成"Dagger+类名"的类
// * 本例中，Man就是目标类，而CarModule就是依赖类，表明要把CarModule注入到Man里面去
// * 一般在这个Component里面定义2中方法：
// * 1、成员注入函数：该方法有一个参数，表示需要注入到的类，提醒Dagger在该类中寻找需要被注入的属性
// * 2、供给函数：该方法没有参数，返回一个需要被注入（或被提供）的依赖。一般用于为其他Component提供依赖的时候
// */
////@Singleton
//@Component(modules = [CarModule::class])
//interface ManComponent {
//    // 成员注入函数：无返回值的注入函数
//    fun injectMan(man: Man)
//
//    // 成员注入函数：有返回值的注入函数
//    fun injectReturnMan(man: Man): Man
//
//    // 成员注入函数：和无返回值的注入函数等价
//    fun getInjector(): MembersInjector<Man>
//}
//
///**
// * Type是Qualifier的一个实现
// * 默认的Qualifier实现是Named
// */
//@Qualifier
//@MustBeDocumented
//@Retention(AnnotationRetention.RUNTIME)
//annotation class Type(val type: String = "default")
//
///**
// * MyScope是Scope的一个实现
// * 默认的Scope实现是Singleton
// */
//@Scope
//@MustBeDocumented
//@Retention(AnnotationRetention.RUNTIME)
//annotation class MyScope
//
//fun main() {
//    val man = Man()
//    //
//    println("${man.car0.name}:${man.car0.hashCode()}")
//    println("${man.car1.name}:${man.car1.hashCode()}")
//    println("car0 == car1 ??? ->> ${man.car0 == man.car1}")
//    //
//    println("${man.car2.get().name}:${man.car2.get().hashCode()}")
//    println("${man.car3.get().name}:${man.car3.get().hashCode()}")
//    println("car2 == car3 ??? ->> ${man.car2.get() == man.car3.get()}")
//
//    for (i in 0..1) {
//        val car = man.car.get()
//        val cars = man.cars.get()
//        println("---------------------------------")
//        println("${car.name}:${car.hashCode()}")
//        println("${cars.name}:${cars.hashCode()}")
//    }
//}