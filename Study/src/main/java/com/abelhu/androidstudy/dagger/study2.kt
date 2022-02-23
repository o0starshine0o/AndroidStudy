package com.abelhu.androidstudy.dagger

import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

// https://blog.yorek.xyz/android/other/dagger2/
fun main() {
    DaggerActivity().onCreate()
}

/**
 * 假设这是个Activity
 */
class DaggerActivity {

    /**
     * 这里如果去掉@Named("a")注解,就会换成另外一个dog注入了
     */
    @Inject
//    @Named("a")
    lateinit var dog: Dog

    @Inject
    lateinit var client: OkHttpClient

    init {
        /**
         * 查看源码可知:
         * 1, DaggerDogComponent使用了建造者模式
         * 2, DaggerDogComponent的inject方法就是利用Dog的默认构造方法生成Dog的实例,然后传入给成员变量
         */

        DaggerDogComponent
            .builder()
            // 因为onCreate()方法中用到了dog,所以必须要把dogModule注入
            .dogModule(DogModule(10))
            // 因为onCreate()方法中木有使用到client,所以这里不用强制注入
//            .okHttpModule(OkHttpModule())
            .build()
            .inject(this)
    }

    fun onCreate() {
        println(dog.drink())
    }
}

class Water @Inject constructor() {
    override fun toString() = "water"
}

/**
 * 在Dog类的无参构造函数上加一个@Inject注解
 */
class Dog @Inject constructor() {
    private var age: Int? = null
    private var water: Water? = null

    constructor(age: Int, water: Water) : this() {
        this.age = age
        this.water = water
    }

    fun drink() = "[$age] drink $water"
}

@Module
class DogModule(private val age: Int) {

    /**
     * 因为返回值都是Dog类型,所以需要通过Name注解下,以示区分
     * 也可以使用@Qualifier注解,需要额外的声明
     */
    @Named("a")
    @Provides
    fun provideDog(water: Water) = Dog(age, water)

    @Provides
    fun provideDog2() = Dog()
}

@Singleton
@Component(modules = [DogModule::class, OkHttpModule::class])
interface DogComponent {
    fun inject(activity: DaggerActivity)
}